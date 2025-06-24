package com.trackswiftly.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Container annotation that allows multiple {@link ValidateOwnership} annotations
 * to be applied to the same method.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateOwnerships {


    /**
     * The array of {@link ValidateOwnership} annotations.
     *
     * @return array of ownership validations
     */
    ValidateOwnership[] value();
}