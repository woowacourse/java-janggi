package janggi.domain.gamestatus;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Point;

public interface GameStatus {

    GameStatus move(Point from, Point to);

    boolean isEndGame();

    Dynasty currentTurn();

    Dynasty winner();

    JanggiBoard janggiBoard();

    static GameStatus of(Dynasty currentTurn, JanggiBoard janggiBoard) {
        if (janggiBoard.isDeadKing(currentTurn)) {
            return new GameEnded(currentTurn.opposite(), janggiBoard);
        }
        if (janggiBoard.isDeadKing(currentTurn.opposite())) {
            return new GameEnded(currentTurn, janggiBoard);
        }
        return new GameRunned(currentTurn, janggiBoard);
    }
}
