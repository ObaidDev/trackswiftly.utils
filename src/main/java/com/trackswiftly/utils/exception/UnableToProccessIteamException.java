package com.trackswiftly.utils.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor  @Getter @Setter 
public class UnableToProccessIteamException extends RuntimeException{
    
    public UnableToProccessIteamException(String message) {
        super(message);  // Pass the message to the superclass (Exception)
    }
    
}
