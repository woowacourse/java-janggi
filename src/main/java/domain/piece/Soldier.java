package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Soldier extends ActivePiece {
    private final List<Integer> dx = List.of(-1, 1);
    private final List<Integer> dy = List.of(0, 0);

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

        for (int i = 0; i < dx.size(); i++) {
            if (dx.get(i) == colDiff && dy.get(i) == rowDiff) {
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
