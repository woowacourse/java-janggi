package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Horse extends Piece {

    private static final List<Integer> ROW_OFFSETS = List.of(1, 2, 2, 1, -1, -2, -2, -1);
    private static final List<Integer> COLUMN_OFFSETS = List.of(2, 1, -1, -2, -2, -1, 1, 2);

    public Horse(Team team) {
        super(team, PieceType.MA);
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

}
