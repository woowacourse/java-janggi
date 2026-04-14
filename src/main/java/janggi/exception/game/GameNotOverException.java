package janggi.exception.game;

import janggi.exception.BusinessException;

public class GameNotOverException extends BusinessException {
    public GameNotOverException() {
        super("게임이 아직 종료되지 않아 승자를 확인할 수 없습니다.");
    }
}
