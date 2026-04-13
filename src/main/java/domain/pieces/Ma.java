package domain.pieces;

import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.InvalidPathException;
import domain.pieces.exception.PieceErrorMessage;
import java.util.List;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import domain.movepolicy.path.PathRule;
import domain.movement.Direction;
import domain.movement.DirectionSequence;
import domain.movement.DirectionSequenceResult;
import domain.position.Position;

public class Ma extends FullPiece {

    private static final List<DirectionSequence> DIRECTION_SEQUENCES = List.of(
            DirectionSequence.of(Direction.UP, Direction.RIGHT_UP),
            DirectionSequence.of(Direction.UP, Direction.LEFT_UP),
            DirectionSequence.of(Direction.DOWN, Direction.RIGHT_DOWN),
            DirectionSequence.of(Direction.DOWN, Direction.LEFT_DOWN),
            DirectionSequence.of(Direction.LEFT, Direction.LEFT_UP),
            DirectionSequence.of(Direction.LEFT, Direction.LEFT_DOWN),
            DirectionSequence.of(Direction.RIGHT, Direction.RIGHT_UP),
            DirectionSequence.of(Direction.RIGHT, Direction.RIGHT_DOWN)
    );

    public Ma(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (!hasDirectionSequenceTo(departure, destination)) {
            throw new InvalidMoveException(PieceErrorMessage.MA_INVALID_MOVE);
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
    public PieceType getType() {
        return PieceType.MA;
    }

    private boolean hasDirectionSequenceTo(Position departure, Position destination) {
        return findDirectionSequenceResult(departure, destination).isPresent();
    }

    private java.util.Optional<DirectionSequenceResult> findDirectionSequenceResult(Position departure,
                                                                                    Position destination) {
        return DIRECTION_SEQUENCES.stream()
                .map(sequence -> safePositionsFrom(sequence, departure))
                .flatMap(java.util.Optional::stream)
                .filter(result -> result.lastPosition().equals(destination))
                .findFirst();
    }

    private java.util.Optional<DirectionSequenceResult> safePositionsFrom(DirectionSequence sequence,
                                                                          Position departure) {
        try {
            return java.util.Optional.of(sequence.positionsFrom(departure));
        } catch (IllegalArgumentException e) {
            return java.util.Optional.empty();
        }
    }
}
