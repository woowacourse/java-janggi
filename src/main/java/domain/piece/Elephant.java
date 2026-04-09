package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.strategy.ForwardAndDiagonal;
import domain.movement.strategy.MoveStrategy;
import java.util.List;

public final class Elephant extends Piece {

    private static final MoveAmount DIAGONAL_MOVE_DISTANCE = new MoveAmount(2);

    private final MoveStrategy moveStrategy = new ForwardAndDiagonal(DIAGONAL_MOVE_DISTANCE);

    public Elephant(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> movableIntersections(
            Intersection from,
            AlivePieces alivePieces
    ) {
        return moveStrategy.getCardinalRoutes(from)
                .stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side))
                .map(Route::getDestination)
                .toList();
    }

    @Override
    public int getScore() {
        return 3;
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
