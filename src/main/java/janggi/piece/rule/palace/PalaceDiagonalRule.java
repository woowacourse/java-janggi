package janggi.piece.rule.palace;

import janggi.board.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;
import janggi.coordinate.Route;

public abstract class PalaceDiagonalRule {

    abstract public boolean isSatisfied(Board board,
                                        Position departure,
                                        Position destination,
                                        Distance distance);

    protected boolean includesPalaceCenter(final Board board,
                                           final Position departure,
                                           final Position destination) {
        return Route.of(departure, destination)
                .calculateWithDepartureAndDestination().stream()
                .anyMatch(board::isCenterOfPalace);
    }
}
