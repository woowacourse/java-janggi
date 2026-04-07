package domain.state;

import domain.Board;
import domain.Position;
import domain.constant.Country;

public interface GameState {
    GameState move(Board board, Position start, Position end);

    boolean isFinished();

    Country getTurn();

    Country getWinner();
}
