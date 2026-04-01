package domain.pieces;

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
        if (findDirectionSequenceResult(departure, destination).isEmpty()) {
            throw new IllegalArgumentException("마의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        return findDirectionSequenceResult(departure, destination)
                .map(DirectionSequenceResult::pathPositions)
                .orElseThrow(() -> new IllegalArgumentException("출발지와 도착지의 좌표가 유효하지 않습니다."));
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
        return PieceType.MA;
    }

    private java.util.Optional<DirectionSequenceResult> findDirectionSequenceResult(Position departure,
                                                                                    Position destination) {
        return DIRECTION_SEQUENCES.stream()
                .map(sequence -> sequence.positionsFrom(departure))
                .filter(result -> result.lastPosition().equals(destination))
                .findFirst();
    }
}
