package domain.pieces;

import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.InvalidPathException;
import domain.pieces.exception.PieceErrorMessage;
import java.util.List;
import java.util.Optional;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import domain.movepolicy.path.PathRule;
import domain.movement.Direction;
import domain.movement.DirectionSequence;
import domain.movement.DirectionSequenceResult;
import domain.position.Position;

public class Sang extends FullPiece {

    private static final List<DirectionSequence> DIRECTION_SEQUENCES = List.of(
            DirectionSequence.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT_UP),
            DirectionSequence.of(Direction.UP, Direction.LEFT_UP, Direction.LEFT_UP),
            DirectionSequence.of(Direction.DOWN, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN),
            DirectionSequence.of(Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT_DOWN),
            DirectionSequence.of(Direction.LEFT, Direction.LEFT_UP, Direction.LEFT_UP),
            DirectionSequence.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.LEFT_DOWN),
            DirectionSequence.of(Direction.RIGHT, Direction.RIGHT_UP, Direction.RIGHT_UP),
            DirectionSequence.of(Direction.RIGHT, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)
    );

    public Sang(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (findDirectionSequenceResult(departure, destination).isEmpty()) {
            throw new InvalidMoveException(PieceErrorMessage.SANG_INVALID_MOVE);
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        return findDirectionSequenceResult(departure, destination)
                .map(DirectionSequenceResult::pathPositions)
                .orElseThrow(() -> new InvalidPathException(PieceErrorMessage.INVALID_PATH));
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
    public boolean isPo() {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.SANG;
    }

    private Optional<DirectionSequenceResult> findDirectionSequenceResult(Position departure, Position destination) {
        return DIRECTION_SEQUENCES.stream()
                .map(sequence -> safePositionsFrom(sequence, departure))
                .flatMap(Optional::stream)
                .filter(result -> result.lastPosition().equals(destination))
                .findFirst();
    }

    private Optional<DirectionSequenceResult> safePositionsFrom(DirectionSequence sequence, Position departure) {
        try {
            return Optional.of(sequence.positionsFrom(departure));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
