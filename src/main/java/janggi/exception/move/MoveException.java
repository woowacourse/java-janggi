package janggi.exception.move;

import janggi.exception.BusinessException;

public abstract class MoveException extends BusinessException {
    protected MoveException(String message) {
        super(message);
    }
}
