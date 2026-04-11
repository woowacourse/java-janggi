package janggi.domain.state;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.board.Board;

public interface GameState {
    GameState move(Position from, Position to, Board board);
    boolean isOngoing();
    Camp turn();
    void validateCamp(Position current, Board board);
    String getStateType();
}
