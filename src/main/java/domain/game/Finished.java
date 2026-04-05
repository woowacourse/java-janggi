package domain.game;

public class Finished implements GameState {
    private static final String FINISHED_GAME_ERROR_MESSAGE = "[ERROR] 이미 끝난 게임입니다.";

    public Finished() {
    }

    @Override
    public void validateMovable() {
        throw new IllegalArgumentException(FINISHED_GAME_ERROR_MESSAGE);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
