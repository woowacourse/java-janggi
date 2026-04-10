package janggi.exception.business;

public class InvalidTeamException extends BusinessException {
    public InvalidTeamException() {
        super("존재하지 않는 팀 이름입니다");
    }
}
