package janggi.controller;

import static janggi.controller.JanggiController.MAXIMUM_GAMES_COUNT_IN_PROGRESS;

import java.util.List;

public class GameSelectCommand {

    private final List<Long> gameInProgressIds;
    private int selectedCommand = -1;

    public GameSelectCommand(final List<Long> gameInProgressIds) {
        this.gameInProgressIds = gameInProgressIds;
    }

    public void select(final int selectedCommand) {
        validateSelectedCommandRange(selectedCommand);
        this.selectedCommand = selectedCommand;
    }

    private void validateSelectedCommandRange(final int selectedCommand) {
        if (gameInProgressIds.size() < MAXIMUM_GAMES_COUNT_IN_PROGRESS
            && (selectedCommand < 0 || selectedCommand > gameInProgressIds.size())) {
            throw new IllegalArgumentException("해당 명령 번호는 유효한 번호가 아닙니다.");
        }
        if (gameInProgressIds.size() >= MAXIMUM_GAMES_COUNT_IN_PROGRESS
            && (selectedCommand < 1 || selectedCommand > gameInProgressIds.size())) {
            throw new IllegalArgumentException("해당 명령 번호는 유효한 번호가 아닙니다.");
        }
    }

    public boolean isGenerateGame() {
        validateCommandSelected();
        return selectedCommand == 0;
    }

    public long getSelectedGameId() {
        validateCommandSelected();
        validateCommandForGame();
        return gameInProgressIds.get(selectedCommand - 1);
    }

    private void validateCommandSelected() {
        if (selectedCommand == -1) {
            throw new IllegalStateException("아직 게임 선택 명령이 입력되지 않았습니다.");
        }
    }

    private void validateCommandForGame() {
        if (selectedCommand == 0) {
            throw new IllegalStateException("게임 불러오기 명령이 아닙니다.");
        }
    }
}
