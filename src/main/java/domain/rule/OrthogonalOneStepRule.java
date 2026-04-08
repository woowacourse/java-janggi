package domain.rule;

import domain.position.Position;
import java.util.List;

public class OrthogonalOneStepRule implements MoveRule {

    private static final List<Integer> ROW_OFFSETS = List.of(-1, 1, 0, 0);
    private static final List<Integer> COLUMN_OFFSETS = List.of(0, 0, -1, 1);

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDifference = target.rowDifference(source);
        int columnDifference = target.columnDifference(source);

        for (int i = 0; i < ROW_OFFSETS.size(); i++) {
            if (ROW_OFFSETS.get(i) == rowDifference && COLUMN_OFFSETS.get(i) == columnDifference) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        return List.of();
    }
}
