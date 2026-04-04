package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Elephant extends Piece {

    private static final List<Integer> ROW_OFFSETS = List.of(2, 3, -2, -3, -3, -2, 2, 3);
    private static final List<Integer> COLUMN_OFFSETS = List.of(3, 2, 3, 2, -2, -3, -3, -2);

    public Elephant(Team team) {
        super(team, PieceType.SANG);
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
        if (source.columnDiff(target) == -3) {
            Position mid = source.addPosition(0, 1);
            return List.of(mid, mid.middlePosition(target));
        }
        if (source.columnDiff(target) == 3) {
            Position mid = source.addPosition(0, -1);
            return List.of(mid, mid.middlePosition(target));
        }
        if (source.rowDiff(target) == -3) {
            Position mid = source.addPosition(1, 0);
            return List.of(mid, mid.middlePosition(target));
        }
        Position mid = source.addPosition(-1, 0);
        return List.of(mid, mid.middlePosition(target));
    }

}
