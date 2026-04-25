package janggi.exception.move;

import janggi.exception.BusinessException;

public class NotYourPieceException extends BusinessException {
    public NotYourPieceException() {
        super("자신의 기물만 이동시킬 수 있습니다.");
    }
}
