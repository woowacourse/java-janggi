package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class General extends ActivePiece {
    private static final List<Integer> rowOffsets = List.of(-1, 1, 0, 0);
    private static final List<Integer> columnOffsets = List.of(0, 0, -1, 1);

    public General(Team team) {
        super(team, PieceType.GENERAL);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (int i = 0; i < rowOffsets.size(); i++) {
            if (rowOffsets.get(i) == rowDiff && columnOffsets.get(i) == colDiff) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        return List.of();
    }
}
