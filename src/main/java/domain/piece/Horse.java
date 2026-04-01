package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Horse extends ActivePiece {

    private static final List<Integer> rowOffsets = List.of(1, 2, 2, 1, -1, -2, -2, -1);
    private static final List<Integer> columnOffsets = List.of(2, 1, -1, -2, -2, -1, 1, 2);

    public Horse(Team team) {
        super(team, PieceType.MA);
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
        if (source.columnDiff(target) == -2) {
            return List.of(source.addPosition(0, 1));
        }

        if (source.columnDiff(target) == 2) {
            return List.of(source.addPosition(0, -1));
        }

        if (source.rowDiff(target) == -2) {
            return List.of(source.addPosition(1, 0));
        }

        return List.of(source.addPosition(-1, 0));
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
