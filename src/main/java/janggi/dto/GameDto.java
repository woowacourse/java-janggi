package janggi.dto;

import janggi.domain.GameStatus;

public record GameDto(Long id, GameStatus gameStatus) {

    public static long convertToIntId(String inputId) {
        try {
            return Long.parseLong(inputId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("게임 ID는 숫자 형태로 입력해야 합니다.");
        }
    }

    public static GameDto of(long id, String gameStatus) {
        return new GameDto(id, GameStatus.from(gameStatus));
    }

    public String gameStatusFormat() {
        return gameStatus.getFormat();
    }
}
