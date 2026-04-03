package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class General extends ActivePiece {
    private static final List<Integer> ROW_OFFSETS = List.of(-1, 1, 0, 0);
    private static final List<Integer> COLUMN_OFFSETS = List.of(0, 0, -1, 1);

    public General(Team team) {
        super(team, PieceType.GENERAL);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (int i = 0; i < ROW_OFFSETS.size(); i++) {
            if (ROW_OFFSETS.get(i) == rowDiff && COLUMN_OFFSETS.get(i) == colDiff) {
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
