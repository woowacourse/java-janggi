package domain.activePiece;

import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.List;

public class Horse extends ActivePiece {

    private final List<Integer> dx = List.of(1, 2, 2, 1, -1, -2, -2, -1);
    private final List<Integer> dy = List.of(2, 1, -1, -2, -2, -1, 1, 2);

    public Horse(Team team) {
        super(team, PieceType.MA);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (int i = 0; i < dx.size(); i++) {
            if (dx.get(i) == rowDiff && dy.get(i) == colDiff) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        if (source.columnDiff(target) == -2) {
            return List.of(source.addPosition(0, 1), target);
        }

        if (source.columnDiff(target) == 2) {
            return List.of(source.addPosition(0, -1), target);
        }

        if (source.rowDiff(target) == -2) {
            return List.of(source.addPosition(1, 0), target);
        }

        return List.of(source.addPosition(-1, 0), target);
    }
}
