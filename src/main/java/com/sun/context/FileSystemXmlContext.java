package com.sun.context;

import com.sun.factory.BeanFactory;
import com.sun.xml.DocumentReader;

import java.util.HashMap;

/**

 *
 * @author sunhuaquan
 * @title my-spring
 * @since 1.0.0
 */
public class FileSystemXmlContext implements BeanFactory {

    private DocumentReader documentReader = DocumentReader.getInstance();

    private HashMap<String, Object> map = new HashMap<>();

    public FileSystemXmlContext(String fileName) {
        documentReader.paresXml(fileName,map);
    }

    @Override
    public Object getBean(String beanName) {

        if (map.containsKey(beanName)) {
            return map.get(beanName);
        }
        throw new RuntimeException(beanName + " is not exist");

    }
}
