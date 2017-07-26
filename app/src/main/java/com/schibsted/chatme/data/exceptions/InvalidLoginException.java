package com.schibsted.chatme.data.exceptions;

/**
 * Created by diego.galico
 */

public class InvalidLoginException extends RuntimeException {

    public InvalidLoginException() {
        super();
    }

    public InvalidLoginException(final String message) {
        super(message);
    }

    public InvalidLoginException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidLoginException(final Throwable cause) {
        super(cause);
    }
}
