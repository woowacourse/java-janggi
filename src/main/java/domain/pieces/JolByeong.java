package domain.pieces;

import domain.board.Palace;
import domain.movement.Direction;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import domain.movepolicy.path.PathRule;
import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.PieceErrorMessage;
import domain.position.Position;
import java.util.List;
import java.util.stream.Collectors;

public class JolByeong extends FullPiece {

    private static final Palace PALACE = new Palace();
    private final Direction forward;
    private final List<Direction> movableDirections;

    public JolByeong(Side side) {
        super(side);
        this.forward = determineForwardDirection(side);
        this.movableDirections = List.of(forward, Direction.LEFT, Direction.RIGHT);
    }

    private Direction determineForwardDirection(Side side) {
        if (side.isCho()) {
            return Direction.UP;
        }
        return Direction.DOWN;
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (movableDestinations(departure).contains(destination)) {
            return;
        }
        if (canMovePalaceDiagonal(departure, destination)) {
            return;
        }
        throw new InvalidMoveException(PieceErrorMessage.JOL_BYEONG_INVALID_MOVE);
    }

    private boolean canMovePalaceDiagonal(Position departure, Position destination) {
        return PALACE.isSingleStepDiagonalConnection(departure, destination)
                && isForwardMove(departure, destination);
    }

    private boolean isForwardMove(Position departure, Position destination) {
        if (forward == Direction.UP) {
            return destination.row() > departure.row();
        }
        return destination.row() < departure.row();
    }

    private List<Position> movableDestinations(Position departure) {
        return movableDirections.stream()
                .filter(direction -> canMove(departure, direction))
                .map(direction -> direction.move(departure))
                .collect(Collectors.toList());
    }

    private boolean canMove(Position position, Direction direction) {
        if (direction == Direction.UP) {
            return position.canMoveUp();
        }
        if (direction == Direction.DOWN) {
            return position.canMoveDown();
        }
        if (direction == Direction.LEFT) {
            return position.canMoveLeft();
        }
        if (direction == Direction.RIGHT) {
            return position.canMoveRight();
        }
        return false;
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        return List.of();
    }

    @Override
    protected DestinationRule getDestinationRule() {
        return new BasicDestinationRule();
    }

    @Override
    protected PathRule getPathRule() {
        return new EmptyPathRule();
    }

    @Override
    public PieceType getType() {
        return PieceType.JOL_BYEONG;
    }
}
