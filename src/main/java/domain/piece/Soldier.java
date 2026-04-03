package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Soldier extends ActivePiece {
    private static final List<Integer> ROW_OFFSETS = List.of(-1, 1);
    private static final List<Integer> COLUMN_OFFSETS = List.of(0, 0);

    public Soldier(Team team) {
        super(team, PieceType.BYEONG);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        if (isValidSideMove(source, target)) {
            return true;
        }
        return target.rowDiff(source) == forwardDirection() && target.columnDiff(source) == 0;
    }

    private boolean isValidSideMove(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (int i = 0; i < ROW_OFFSETS.size(); i++) {
            if (ROW_OFFSETS.get(i) == colDiff && COLUMN_OFFSETS.get(i) == rowDiff) {
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
