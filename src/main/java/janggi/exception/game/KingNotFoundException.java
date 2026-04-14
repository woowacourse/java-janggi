package janggi.exception.game;

import janggi.exception.BusinessException;

public class KingNotFoundException extends BusinessException {
    public KingNotFoundException() {
        super("보드 위에 왕이 존재하지 않습니다.");
    }
}
