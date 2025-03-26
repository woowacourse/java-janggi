package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.moveStrategy.FixedRangeMoveStrategy;
import janggi.domain.piece.moveStrategy.MoveStrategy;

public abstract class Soldier implements Piece{

    protected final MoveStrategy moveStrategy = new FixedRangeMoveStrategy();

    public abstract boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Point start, Point end);
}
