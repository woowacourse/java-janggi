package janggi.domain.rule.block;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Route;

public interface BlockStrategy {
    void validateIsBlock(final Board board, final Position departure, final Position destination);

    default int countPieceInRoute(final Board board, final Position departure, final Position destination) {
        return (int) Route.of(departure, destination).stream()
                .filter(board::isExists)
                .count();
    }
}
