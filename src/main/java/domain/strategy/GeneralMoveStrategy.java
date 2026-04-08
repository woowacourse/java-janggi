package domain.strategy;

import domain.board.Board;
import domain.vo.Position;

public class GeneralMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (!board.isInOwnPalace(from) && !board.isInOwnPalace(to))
            return false;

        if (!from.isOneStepStraightTo(to)
                && !MoveValidator.canMoveOneStepDiagonal(from, to, board))
            return false;

        return board.canOccupy(from, to);
    }
}
