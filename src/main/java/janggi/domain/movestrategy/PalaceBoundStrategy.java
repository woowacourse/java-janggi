package janggi.domain.movestrategy;

import janggi.domain.board.BoardState;
import janggi.domain.position.Position;

public class PalaceBoundStrategy implements MoveStrategy {
    private final MoveStrategy moveStrategy;

    public PalaceBoundStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        if (!isInPalace(to)) {
            return false;
        }
        if (isDiagonalMove(from, to) && !isCenterConnected(from, to)) {
            return false;
        }
        return moveStrategy.canMove(from, to, boardState);
    }

    private boolean isInPalace(Position to) {
        int rowTo = to.getRow();
        int columnTo = to.getColumn();

        boolean inColumn = ((columnTo >= 3) && (columnTo <= 5));
        boolean inHanPalace = ((rowTo >= 0) && (rowTo <= 2));
        boolean inChoPalace = ((rowTo >= 7) && (rowTo <= 9));

        return inColumn && (inHanPalace || inChoPalace);
    }

    private boolean isDiagonalMove(Position from, Position to) {
        return from.getRow() != to.getRow() && from.getColumn() != to.getColumn();
    }

    private boolean isCenterConnected(Position from, Position to) {
        return isCenter(from) || isCenter(to);
    }

    private boolean isCenter(Position pos) {
        return (pos.getRow() == 1 && pos.getColumn() == 4) ||
                (pos.getRow() == 8 && pos.getColumn() == 4);
    }
}
