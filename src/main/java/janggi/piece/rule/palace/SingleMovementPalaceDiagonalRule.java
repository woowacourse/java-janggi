package janggi.piece.rule.palace;

import janggi.board.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;
import janggi.piece.rule.movement.SingleMovementRule;

public class SingleMovementPalaceDiagonalRule extends PalaceDiagonalRule {

    @Override
    public boolean isSatisfied(final Board board,
                               final Position departure,
                               final Position destination,
                               final Distance distance) {
        return board.isPalace(departure)
                && board.isPalace(destination)
                && distance.isDiagonal()
                && distance.getDiagonal() == SingleMovementRule.SINGLE_STEP
                && includesPalaceCenter(board, departure, destination);
    }
}
