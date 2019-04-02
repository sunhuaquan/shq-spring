package com.sun.reflect;

import java.lang.reflect.Field;

/**
 * @author sunhuaquan
 * @Title: ReflectHelper
 * @ProjectName my-spring
 * @Description: TODO
 * @date 2019/3/3121:50
 */
public class ReflectHelper {


    public static Object getNewInstannce(String clazz) throws Exception {
        ClassLoader classLoader = ReflectHelper.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass(clazz);
        Object o = aClass.newInstance();
        return o;
    }

    public static void setField(Object object, String field, Object value) throws Exception {
        Class<?> aClass = object.getClass();
        Field f = aClass.getDeclaredField(field);
        f.setAccessible(true);
        Class fieldType = f.getType();
        if (fieldType == Integer.class) {
            f.set(object, Integer.valueOf(value.toString()));
        } else if (fieldType == Byte.class) {
            f.set(object, Byte.valueOf(value.toString()));
        } else if (fieldType == Short.class) {
            f.set(object, Short.valueOf(value.toString()));
        } else if (fieldType == Boolean.class) {
            f.set(object, Boolean.valueOf(value.toString()));
        } else if (fieldType == Character.class) {
            f.set(object, value.toString());
        } else if (fieldType == Float.class) {
            f.set(object, Float.valueOf(value.toString()));
        } else if (fieldType == Double.class) {
            f.set(object, Double.valueOf(value.toString()));
        } else if (fieldType == Long.class) {
            f.set(object, Long.valueOf(value.toString()));
        } else {
            f.set(object, value);
        }
    }
}
