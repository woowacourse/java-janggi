package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Elephant extends ActivePiece {

    private final List<Integer> dx = List.of(2, 3, -2, -3, -3, -2, 2, 3);
    private final List<Integer> dy = List.of(3, 2, 3, 2, -2, -3, -3, -2);

    public Elephant(Team team) {
        super(team, PieceType.SANG);
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
