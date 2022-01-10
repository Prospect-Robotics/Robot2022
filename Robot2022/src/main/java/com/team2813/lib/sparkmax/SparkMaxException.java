package com.team2813.lib.sparkmax;

import com.revrobotics.REVLibError;

public class SparkMaxException extends Exception {

    REVLibError canError;
    String message;

    public SparkMaxException(REVLibError canError, String message) {
        super();
        this.canError = canError;
        this.message = message;
    }

    public SparkMaxException(REVLibError canError) {
        this(canError, canError.toString());
    }

    public SparkMaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public SparkMaxException(String message, SparkMaxException cause) {
        super(message, cause);
        canError = cause.canError;
    }

    public static void throwIfNotOk(String message, REVLibError canError) throws SparkMaxException {
        if (canError != REVLibError.kOk) {
            throw new SparkMaxException(canError, message);
        }
    }

    public static void throwIfNotOk(REVLibError canError) throws SparkMaxException {
        if (canError != REVLibError.kOk) {
            throw new SparkMaxException(canError);
        }
    }

    public REVLibError getError() {
        return canError;
    }

}
