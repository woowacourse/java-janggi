package janggi.domain.state;

import janggi.domain.Camp;
import janggi.domain.JanggiPosition;
import janggi.domain.board.Board;

public interface GameState {
    GameState move(JanggiPosition from, JanggiPosition to, Board board);

    boolean isOngoing();

    Camp turn();

    void validateCamp(JanggiPosition current, Board board);

    String getStateType();

    GameState giveUp();

    GameState draw();
}
