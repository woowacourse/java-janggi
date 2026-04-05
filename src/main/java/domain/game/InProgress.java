package domain.game;

import domain.piece.Camp;

public class InProgress implements GameState {
    private static final String NONE_FINISHED_GAME_ERROR_MESSAGE = "[ERROR] 아직 끝나지 않은 게임입니다.";

    @Override
    public void validateMovable() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Camp winner() {
        throw new IllegalArgumentException(NONE_FINISHED_GAME_ERROR_MESSAGE);
    }
}
