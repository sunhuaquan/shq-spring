package com.sun.xml;

/**
 * @author sunhuaquan
 * @Title: Property
 * @ProjectName my-spring
 * @Description: TODO
 * @date 2019/3/3122:28
 */
public class Property {

    private String id;

    private String ref;

    private String value;

    public Property(String id, String ref, String value) {
        this.id = id;
        this.ref = ref;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
