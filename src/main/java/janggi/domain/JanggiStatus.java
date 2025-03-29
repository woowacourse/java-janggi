package janggi.domain;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Point;

public interface JanggiStatus {

    JanggiStatus play(Point from, Point to);

    boolean isEndGame();

    Dynasty currentTurn();

    Dynasty winner();

    JanggiBoard janggiBoard();
}
