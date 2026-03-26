package janggi.domain.piece;

import janggi.domain.board.BoardInterface;
import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends BasePiece {
    private final List<List<Movement>> MOVE_RANGE;

    public Pawn(Side side, List<List<Movement>> moveRange) {
        super(new ClearPathPolicy(), side);
        MOVE_RANGE = moveRange;
    }

    public static Pawn from(Side side) {
        List<List<Movement>> moveRange = new ArrayList<>(List.of(List.of(Movement.LEFT), List.of(Movement.RIGHT)));
        moveRange.add(calculateForwardMovement(side));
        return new Pawn(side, moveRange);
    }

    private static List<Movement> calculateForwardMovement(Side side){
        if (side.equals(Side.CHO)) {
            return List.of(Movement.UP);
        }
        return List.of(Movement.DOWN);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        for (List<Movement> movements : MOVE_RANGE) {
            List<Position> calculatedPath = calculatePath(start, movements);
            if (calculatedPath.getLast().equals(end)) {
                return calculatedPath;
            }
        }
        throw new IllegalArgumentException("올바른 도착 지점이 아닙니다.");
    }

    @Override
    public boolean isMovable(List<Position> path, BoardInterface boardInterface) {
        return routePolicy.isMovable(path, side, boardInterface);
    }

    private List<Position> calculatePath(Position start, List<Movement> path) {
        List<Position> calculatedPath = new ArrayList<>(List.of(start));
        for (Movement movement : path) {
            Position step = calculatedPath.getLast().move(movement);
            calculatedPath.add(step);
        }
        return calculatedPath;
    }
}
