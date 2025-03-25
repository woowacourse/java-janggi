package domain.piece.path;

import domain.Movement;
import domain.Position;
import java.util.List;

public class FixedPatternPathFinder implements PathFinder {

    private final List<Movement> movements;

    public FixedPatternPathFinder(List<Movement> movements) {
        this.movements = movements;
    }

    @Override
    public List<Position> findIntermediatePositions(Position from, Position to) {
        return movements.stream()
                .filter(movement -> movement.isValidMove(from, to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다."))
                .findIntermediatePositions(from, to);
    }
}
