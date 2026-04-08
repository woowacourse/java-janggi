package domain.rule;

import domain.position.Position;
import java.util.List;
import java.util.stream.IntStream;

public class LShapeRule implements MoveRule {

    private static final List<Integer> ROW_OFFSETS = List.of(1, 2, 2, 1, -1, -2, -2, -1);
    private static final List<Integer> COLUMN_OFFSETS = List.of(2, 1, -1, -2, -2, -1, 1, 2);

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDifference = target.rowDifference(source);
        int columnDifference = target.columnDifference(source);

        return IntStream.range(0, ROW_OFFSETS.size())
                .anyMatch(i -> ROW_OFFSETS.get(i) == rowDifference && COLUMN_OFFSETS.get(i) == columnDifference);
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        int rowDifference = source.rowDifference(target);
        int columnDifference = source.columnDifference(target);
        return List.of(calculateFirstStep(source, rowDifference, columnDifference));
    }

    private Position calculateFirstStep(Position source, int rowDifference, int columnDifference) {
        if (Math.abs(columnDifference) == 2) {
            return source.addPosition(0, -Integer.signum(columnDifference));
        }
        return source.addPosition(-Integer.signum(rowDifference), 0);
    }
}
