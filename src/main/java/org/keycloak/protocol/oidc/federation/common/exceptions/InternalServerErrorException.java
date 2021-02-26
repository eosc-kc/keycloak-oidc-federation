package org.keycloak.protocol.oidc.federation.common.exceptions;


public class InternalServerErrorException extends RuntimeException {

	private static final long serialVersionUID = 2855684997179997753L;

	public InternalServerErrorException() {
	}

	public InternalServerErrorException(String message) {
		super(message);
	}

}
