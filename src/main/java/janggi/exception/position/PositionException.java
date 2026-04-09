package janggi.exception.position;

import janggi.exception.BusinessException;

public abstract class PositionException extends BusinessException {
    protected PositionException(String message) {
        super(message);
    }
}
