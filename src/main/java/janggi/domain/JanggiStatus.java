package janggi.domain;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Point;

public interface JanggiStatus {

    JanggiStatus move(Point from, Point to);

    boolean isEndGame();

    Dynasty currentTurn();

    Dynasty winner();

    JanggiBoard janggiBoard();

    static JanggiStatus of(Dynasty currentTurn, JanggiBoard janggiBoard) {
        if (janggiBoard.isDeadKing(currentTurn.opposite())) {
            return new JanggiEnded(currentTurn, janggiBoard);
        }
        if (janggiBoard.isDeadKing(currentTurn)) {
            return new JanggiEnded(currentTurn.opposite(), janggiBoard);
        }
        return new JanggiRunned(currentTurn, janggiBoard);
    }
}
