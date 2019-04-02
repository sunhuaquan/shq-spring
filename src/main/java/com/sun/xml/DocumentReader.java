package com.sun.xml;

/**
 * @author sunhuaquan
 * @title testdemo
 * @since 1.0.0
 */

import com.sun.execption.XmlExecption;
import com.sun.reflect.ReflectHelper;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

/**
 * 用DOM方式读取xml文件
 *
 * @author lune
 */
public class DocumentReader {

    private static final String ROOT_NAME = "beans";

    private static final String BEAN_NAME = "bean";

    private static volatile DocumentReader documentReader;

    private Map<String, List<Property>> protertyMap = new HashMap<>();

    public static DocumentReader getInstance() {
        if (documentReader == null) {
            synchronized (DocumentReader.class) {
                if (documentReader == null) {
                    documentReader = new DocumentReader();
                }
            }
        }
        return documentReader;
    }

    private DocumentReader() {

        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            db = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static DocumentBuilderFactory dbFactory = null;
    private static DocumentBuilder db = null;

    public void paresXml(String fileName, HashMap<String, Object> map) {
        try {
            Document document = db.parse(fileName);
            Element root = document.getDocumentElement();
            if (BEAN_NAME.equals(root.getTagName())) {
                throw new XmlExecption("xnl root must be " + BEAN_NAME);
            }
            NodeList childNodes = root.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                paresBean(item, map);
            }
            //解析完成后，设置ref属性
            Set<Map.Entry<String, List<Property>>> entries = protertyMap.entrySet();
            for (Map.Entry<String, List<Property>> entries1 : entries) {

                String key = entries1.getKey();
                List<Property> value = entries1.getValue();
                Object object = map.get(key);
                for (Property property : value) {
                    if (property.getValue() != null) {
                        ReflectHelper.setField(object, property.getId(), property.getValue());

                    } else {
                        Object v = map.get(property.getRef());
                        ReflectHelper.setField(object, property.getId(), v);
                    }
                }
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void paresBean(Node item, HashMap<String, Object> map) throws Exception {

        String nodeName = item.getNodeName();
        if (!BEAN_NAME.equals(nodeName)) {
            //throw new XmlExecption("xml tag " + nodeName + " is not support");
            return;
        }
        NamedNodeMap attributes = item.getAttributes();
        String id = null;
        String clazz = null;

        for (int j = 0; j < attributes.getLength(); j++) {
            Node attr = attributes.item(j);
            //获取属性名
            if ("id".equals(attr.getNodeName())) {
                id = attr.getNodeValue();
            } else if ("class".equals(attr.getNodeName())) {
                clazz = attr.getNodeValue();
            }
        }
        if (id == null || clazz == null) {
            throw new XmlExecption("bean attributes id or calss must have be");
        }
        if (map.containsKey(id)) {
            throw new XmlExecption("bean " + id + "already exist ");
        }
        Object newInstannce = ReflectHelper.getNewInstannce(clazz);
        map.put(id, newInstannce);
        //解析property
        NodeList propertys = item.getChildNodes();
        List<Property> list = new ArrayList<>();
        for (int j = 0; j < propertys.getLength(); j++) {
            Property property = paresProperty(propertys.item(j));
            if (property != null) {
                list.add(property);
            }
        }
        protertyMap.put(id, list);
    }

    private Property paresProperty(Node item) throws Exception {

        String nodeName = item.getNodeName();
        if (!"property".equals(nodeName)) {
            return null;
        }
        NamedNodeMap attributes = item.getAttributes();
        String id = null;
        String ref = null;
        String value = null;
        for (int j = 0; j < attributes.getLength(); j++) {
            Node attr = attributes.item(j);
            //获取属性名
            if ("id".equals(attr.getNodeName())) {
                id = attr.getNodeValue();
            } else if ("ref".equals(attr.getNodeName())) {
                ref = attr.getNodeValue();
            } else if ("value".equals(attr.getNodeName())) {
                value = attr.getNodeValue();
            }
        }
        if (ref != null && value != null) {
            throw new XmlExecption("attr ref and value repeat");
        }
        return new Property(id, ref, value);
    }
}
