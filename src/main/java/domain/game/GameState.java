package domain.game;

import domain.piece.Camp;

public interface GameState {
    void validateMovable();

    boolean isFinished();

    Camp winner();
}
