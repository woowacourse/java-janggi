package domain.piece;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    private static final MoveAmount MOVE_UNIT = new MoveAmount(1);

    public Elephant(Side side) {
        super(side);
    }

    public boolean canMove(
            Intersection from,
            Intersection to,
            AlivePieces alivePieces
    ) {
        return movableIntersections(from, alivePieces)
                .contains(to);
    }

    public List<Intersection> movableIntersections(
            Intersection from,
            AlivePieces alivePieces
    ) {
        List<Intersection> movableIntersections = new ArrayList<>();
        for (Direction direction : side.getAllDirections()) {
            addIfMovable(from, direction, alivePieces, movableIntersections);
        }

        return List.copyOf(movableIntersections);
    }

    private void addIfMovable(
            Intersection from,
            Direction direction,
            AlivePieces alivePieces,
            List<Intersection> movableIntersections
    ) {
        addLeftDestinationIfReachable(from, direction, alivePieces, movableIntersections);
        addRightDestinationIfReachable(from, direction, alivePieces, movableIntersections);
    }

    private void addLeftDestinationIfReachable(
            Intersection from,
            Direction direction,
            AlivePieces alivePieces,
            List<Intersection> movableIntersections
    ) {
        List<Intersection> path = pathToLeftDestination(from, direction);
        Intersection destination = direction.moveForwardLeft(path.getLast(), MOVE_UNIT);

        if (isAvailablePath(alivePieces, path) && isAvailableDestination(destination, alivePieces)) {
            movableIntersections.add(destination);
        }
    }

    private void addRightDestinationIfReachable(
            Intersection from,
            Direction direction,
            AlivePieces alivePieces,
            List<Intersection> movableIntersections
    ) {
        List<Intersection> path = pathToRightDestination(from, direction);
        Intersection destination = direction.moveForwardRight(path.getLast(), MOVE_UNIT);

        if (isAvailablePath(alivePieces, path) && isAvailableDestination(destination, alivePieces)) {
            movableIntersections.add(destination);
        }
    }

    private List<Intersection> pathToLeftDestination(Intersection from, Direction direction) {
        Intersection firstNode = direction.moveForward(from, MOVE_UNIT);
        Intersection secondNode = direction.moveForwardLeft(firstNode, MOVE_UNIT);

        return List.of(firstNode, secondNode);
    }

    private List<Intersection> pathToRightDestination(Intersection from, Direction direction) {
        Intersection firstNode = direction.moveForward(from, MOVE_UNIT);
        Intersection secondNode = direction.moveForwardRight(firstNode, MOVE_UNIT);

        return List.of(firstNode, secondNode);
    }

    private static boolean isAvailablePath(AlivePieces alivePieces, List<Intersection> path) {
        return path.stream()
                .allMatch(node -> node.isInBoard() && alivePieces.isEmpty(node));
    }

    private boolean isAvailableDestination(Intersection destination, AlivePieces alivePieces) {
        return destination.isInBoard() && alivePieces.placedNotSameSide(destination, side);
    }
}
