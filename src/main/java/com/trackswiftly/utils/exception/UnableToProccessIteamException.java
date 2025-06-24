package com.trackswiftly.utils.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * Exception thrown when an item cannot be processed successfully.
 */
@NoArgsConstructor  @Getter @Setter 
public class UnableToProccessIteamException extends RuntimeException{
    

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message explaining the cause
     */
    public UnableToProccessIteamException(String message) {
        super(message);
    }
    
}
