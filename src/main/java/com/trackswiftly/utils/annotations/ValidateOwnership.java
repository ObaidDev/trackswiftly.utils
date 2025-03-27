package com.trackswiftly.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidateOwnerships.class)
public @interface ValidateOwnership {
    Class<?> entity();
    String pathToId();
    ValidationType validationType() default ValidationType.EXISTS;
    
    enum ValidationType {
        EXISTS, BELONGS_TO_TENANT
    }
}
