package org.example;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateTree {
    int minValue() default -1;
    int maxValue() default -1;
    String message() default "Invalid tree field";
}
