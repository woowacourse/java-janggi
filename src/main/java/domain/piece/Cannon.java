package domain.piece;

import domain.Direction;
import domain.Palace;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.ArrayList;
import java.util.List;

import static constant.ErrorMessage.*;

public class Cannon extends Piece {

    private static final List<List<Direction>> PATHS = List.of(
            List.of(Direction.UP), List.of(Direction.DOWN),
            List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Cannon(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position source) {
        Palace palace = Palace.getInstance();
        List<Position> routes = new ArrayList<>(movementStrategy.findRoute(PATHS, source));
        if (palace.isInPalace(source)) {
            routes.addAll(palace.findDiagonalRoutes(source));
        }
        return routes;
    }

    @Override
    public List<Position> findPathTo(Position source, Position target) {
        if (source.isDiagonalTo(target)) {
            return Palace.getInstance().findDiagonalPathTo(source, target);
        }
        return movementStrategy.findPathTo(PATHS, source, target);
    }

    @Override
    public void checkRoute(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece.isCannon()) {
                throw new IllegalArgumentException(CANNOT_JUMP_WITH_CANNON);
            }
        }
        if (!isValidRoute(pieces)) {
            throw new IllegalArgumentException(MUST_JUMP_EXACTLY_ONE);
        }
    }

    @Override
    public boolean isValidRoute(List<Piece> pieces) {
        int jumpedPieceCount = 0;
        for (Piece piece : pieces) {
            if (piece.isCannon()) {
                return false;
            }
            if (!piece.isEmpty()) {
                jumpedPieceCount++;
            }
        }
        return jumpedPieceCount == 1;
    }

    @Override
    public boolean isValidTarget(Piece targetPiece) {
        if (targetPiece.isCannon()) {
            return false;
        }
        return super.isValidTarget(targetPiece);
    }

    @Override
    public void checkTarget(Piece targetPiece) {
        super.checkTarget(targetPiece);
        if (targetPiece.isCannon()) {
            throw new IllegalArgumentException(CANNOT_CAPTURE_CANNON_WITH_CANNON);
        }
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public String getName() {
        return "包";
    }

    @Override
    public double getScore() {
        return 7;
    }
}
