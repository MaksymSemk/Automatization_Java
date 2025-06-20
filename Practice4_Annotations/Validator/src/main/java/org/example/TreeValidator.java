package org.example;

import java.lang.reflect.Field;

public class TreeValidator {

    public static boolean validate(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ValidateTree.class)) {
                try {
                    Object value = field.get(obj);
                    if (value == null || value.toString().isBlank()) {
                        System.out.println("Validation failed for field: " + field.getName());
                        return false;
                    }
                    ValidateTree ann = field.getAnnotation(ValidateTree.class);
                    if ( (value instanceof Integer) &&
                            ann.minValue()>-1 &&
                            ann.maxValue()>-1 &&
                            ((int)value< ann.minValue() || (int)value> ann.maxValue())){
                        System.out.println("Validation failed for field: " + field.getName());
                        System.out.println("Field: \'" +field.getName() + "\' must be in ["+ ann.minValue()+" ,"+ann.maxValue()+"]");
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }
}
