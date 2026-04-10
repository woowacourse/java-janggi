package janggi.domain.movestrategy;

import janggi.domain.board.BoardState;
import janggi.domain.movestrategy.route.Route;
import janggi.domain.position.Position;

import java.util.List;

public class FixedStepMoveStrategy implements MoveStrategy {
    private final List<Route> possibleRoutes;

    public FixedStepMoveStrategy(List<Route> possibleRoutes) {
        this.possibleRoutes = possibleRoutes;
    }

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        return possibleRoutes.stream()
                .filter(route -> route.isMatch(from, to))
                .findFirst()
                .map(route -> route.isPathClear(from, boardState))
                .orElse(false);
    }
}

