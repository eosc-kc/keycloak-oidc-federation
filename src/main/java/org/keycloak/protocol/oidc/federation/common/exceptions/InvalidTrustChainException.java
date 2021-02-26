package org.keycloak.protocol.oidc.federation.common.exceptions;

public class InvalidTrustChainException extends Exception {

    public InvalidTrustChainException(String message) {
        super(message);
    }
}
