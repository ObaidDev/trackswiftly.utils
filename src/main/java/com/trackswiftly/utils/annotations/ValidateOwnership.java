package com.trackswiftly.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Annotation to validate ownership of an entity before executing a method.
 * <p>
 * This annotation can be applied multiple times on the same method
 * via the {@link ValidateOwnerships} container.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidateOwnerships.class)
public @interface ValidateOwnership {


    /**
     * The entity class to validate ownership for.
     *
     * @return the entity class
     */
    Class<?> entity();


    /**
     * The path expression to the ID within the request object.
     *
     * @return the path to the ID
     */
    String pathToId();


    /**
     * The type of validation to perform.
     *
     * @return the validation type (default is {@link ValidationType#EXISTS})
     */
    ValidationType validationType() default ValidationType.EXISTS;
    


     /**
     * Enum representing types of ownership validations.
     */
    enum ValidationType {

        /** Validate that the entity exists. */
        EXISTS, 
        

        /** Validate that the entity belongs to the tenant. */
        BELONGS_TO_TENANT
    }
}
