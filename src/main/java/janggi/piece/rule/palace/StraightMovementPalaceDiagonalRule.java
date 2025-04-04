package janggi.piece.rule.palace;

import janggi.board.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;

public class StraightMovementPalaceDiagonalRule extends PalaceDiagonalRule {

    @Override
    public boolean isSatisfied(final Board board,
                               final Position departure,
                               final Position destination,
                               final Distance distance) {
        return board.isPalace(departure)
                && board.isPalace(destination)
                && distance.isDiagonal()
                && includesPalaceCenter(board, departure, destination);
    }
}
