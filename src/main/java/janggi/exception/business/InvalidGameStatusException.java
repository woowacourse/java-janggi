package janggi.exception.business;

public class InvalidGameStatusException extends BusinessException {
    public InvalidGameStatusException() {
        super("존재하지 않는 게임 상태입니다.");
    }
}
