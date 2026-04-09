package janggi.controller;

import static janggi.controller.JanggiController.MAXIMUM_GAMES_COUNT_IN_PROGRESS;

import java.util.List;

public class GameSelectCommand {

    private final long selectedGameId;

    public static GameSelectCommand from(final int selectedNumber, final List<Long> gameIds) {
        validateSelectedNumberRange(selectedNumber, gameIds.size());
        if (selectedNumber == 0) {
            return new GameSelectCommand(0);
        }
        return new GameSelectCommand(gameIds.get(selectedNumber - 1));
    }

    private static void validateSelectedNumberRange(final int selectedNumber, final int idsCount) {
        if (idsCount < MAXIMUM_GAMES_COUNT_IN_PROGRESS
            && (selectedNumber < 0 || selectedNumber > idsCount)) {
            throw new IllegalArgumentException("해당 명령 번호는 유효한 번호가 아닙니다.");
        }
        if (idsCount >= MAXIMUM_GAMES_COUNT_IN_PROGRESS
            && (selectedNumber < 1 || selectedNumber > idsCount)) {
            throw new IllegalArgumentException("해당 명령 번호는 유효한 번호가 아닙니다.");
        }
    }

    private GameSelectCommand(final long selectedGameId) {
        this.selectedGameId = selectedGameId;
    }

    public boolean isGenerateGame() {
        return selectedGameId == 0;
    }

    public long getSelectedGameId() {
        validateGenerateGame();
        return selectedGameId;
    }

    private void validateGenerateGame() {
        if (isGenerateGame()) {
            throw new IllegalStateException("게임 불러오기 명령이 아닙니다.");
        }
    }
}
