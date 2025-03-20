package janggi.piece.strategy.block;

import janggi.Board;
import janggi.Position;
import janggi.Route;

public class RequiredBlockCountStrategy implements BlockStrategy {

    private final int requireBlockCount;

    public RequiredBlockCountStrategy(final int requireBlockCount) {
        this.requireBlockCount = requireBlockCount;
    }

    public static BlockStrategy common() {
        return new RequiredBlockCountStrategy(0);
    }

    public void validate(final Board board, final Position departure, final Position destination) {
        if (countPieceInRoute(board, departure, destination) != requireBlockCount) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private int countPieceInRoute(Board board, Position departure, Position destination) {
        return (int) Route.of(departure, destination).stream()
                .filter(board::isExists)
                .count();
    }
}
