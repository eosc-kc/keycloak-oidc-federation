package org.keycloak.protocol.oidc.federation.common.exceptions;

public class BadSigningOrEncryptionException extends Exception {

	public BadSigningOrEncryptionException(String message) {
		super(message);
	}
	
}
