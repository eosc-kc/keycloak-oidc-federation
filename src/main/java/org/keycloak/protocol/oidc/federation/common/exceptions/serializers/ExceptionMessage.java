package org.keycloak.protocol.oidc.federation.common.exceptions.serializers;

public class ExceptionMessage {

    private String message;
    private String internal;
    
    public ExceptionMessage (String message) {
        this.message = message;
        this.internal = message;
    }
    
    public ExceptionMessage (Exception e) {
        internal = e.getMessage();
        this.message = e.getMessage();
    }
    
    public ExceptionMessage (String message, Exception e) {
        internal = e.getMessage();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getInternal() {
        return internal;
    }
    
}
