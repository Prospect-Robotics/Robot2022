package com.team2813.lib.ctre;

import com.ctre.phoenix.ErrorCode;

import java.util.HashMap;
import java.util.Map;

public class CTREException extends Exception {

    private ErrorCode errorCode;

    private String message;

    private static final Map<Integer, String> errorDescriptions = new HashMap<>();

    static {
        errorDescriptions.put(-1, "Could not transmit the CAN frame.");
        errorDescriptions.put(-2, "Caller passed an invalid param");
        errorDescriptions.put(-3, "CAN frame has not been received within specified period of time.");
        errorDescriptions.put(-5, "Specified Device ID is invalid.");
        errorDescriptions.put(+6, "Caller attempted to insert data into a buffer that is full.");
        errorDescriptions.put(-7, "Sensor is not present");
        errorDescriptions.put(-200, "Have not received an value response for signal.");
    }

    public CTREException(ErrorCode errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public CTREException(ErrorCode errorCode) {
        this(errorCode, errorCode.toString());
    }

    public CTREException(String message, Throwable cause) {
        super(message, cause);
    }

    public CTREException(String message, CTREException cause) {
        super(message, cause);
        errorCode = cause.errorCode;
    }

    public static void throwIfNotOk(String message, ErrorCode errorCode) throws CTREException {
        if (errorCode.value != ErrorCode.OK.value && errorCode.value != -3) {
            throw new CTREException(errorCode, message);
        }
    }

    public static void throwIfNotOk(ErrorCode errorCode) throws CTREException {
        if (errorCode.value != ErrorCode.OK.value && errorCode.value != -3) {
            throw new CTREException(errorCode);
        }
    }

    @Override
    public String getMessage() {
        // CAN_MSG_NOT_FOUND (-3) CAN frame has not been received within specified period of time.
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append(' ');
        sb.append(errorCode.toString());
        sb.append('(');
        sb.append(errorCode.value);
        sb.append(')');
        if(errorDescriptions.containsKey(errorCode.value)){
            sb.append(' ');
            sb.append(errorDescriptions.get(errorCode.value));
        }
        return sb.toString();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
