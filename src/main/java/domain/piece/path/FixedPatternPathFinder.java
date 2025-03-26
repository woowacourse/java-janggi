package domain.piece.path;

import domain.position.Movement;
import domain.position.Position;
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
                .orElseThrow(() -> new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다."))
                .findIntermediatePositions(from, to);
    }
}
