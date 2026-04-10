package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import domain.movement.strategy.ForwardAndDiagonal;
import domain.movement.strategy.MoveStrategy;
import java.util.List;

public final class Horse extends Piece {

    private static final MoveAmount DIAGONAL_MOVE_DISTANCE = new MoveAmount(1);

    private final MoveStrategy moveStrategy = new ForwardAndDiagonal(DIAGONAL_MOVE_DISTANCE);

    public Horse(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> movableIntersections(
            Intersection from,
            AlivePieces alivePieces
    ) {
        return moveStrategy.getRoutes(from, Vector.cardinals())
                .stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side))
                .map(Route::getDestination)
                .toList();
    }

    @Override
    public double getScore() {
        return 5;
    }

    @Override
    public boolean canBelongToWing() {
        return true;
    }

    @Override
    public boolean isRoyalPiece() {
        return false;
    }

    @Override
    protected boolean isScreenable() {
        return true;
    }
}
