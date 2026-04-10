package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

import java.util.ArrayList;
import java.util.List;

public abstract class StepPiece extends ActivePiece {
    private final List<List<Movement>> baseMoveRange;

    public StepPiece(List<List<Movement>> baseMoveRange, RoutePolicy routePolicy, PalaceTopology palaceTopology, Side side, PieceType pieceType) {
        super(routePolicy, palaceTopology, side, pieceType);
        this.baseMoveRange = baseMoveRange;
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        return candidateMoves(start).stream()
                .map(movements -> calculatePath(start, movements))
                .filter(path -> path.getLast().equals(end))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_DESTINATION_MESSAGE));
    }

    private List<Position> calculatePath(Position start, List<Movement> path) {
        List<Position> calculatedPath = new ArrayList<>(List.of(start));
        for (Movement movement : path) {
            Position step = calculatedPath.getLast().move(movement);
            calculatedPath.add(step);
        }
        return calculatedPath;
    }

    private List<List<Movement>> candidateMoves(Position position){
        List<List<Movement>> moves = new ArrayList<>(baseMoveRange);
        moves.addAll(palaceTopology.diagonalOneStepMovements(position));
        return moves;
    }
}
