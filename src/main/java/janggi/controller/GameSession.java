package janggi.controller;

public record GameSession(Long gameId) {

    public GameSession {
        validate(gameId);
    }

    private void validate(Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("게임 식별자는 null일 수 없습니다.");
        }
        if (gameId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 게임 식별자입니다: " + gameId);
        }
    }
}
