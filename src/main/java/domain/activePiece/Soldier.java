package domain.activePiece;

import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.List;

public class Soldier extends ActivePiece {
    private final List<Integer> dx = List.of(-1, 1);
    private final List<Integer> dy = List.of(0, 0);

    protected Soldier(Team team) {
        super(team, PieceType.BYEONG);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        boolean sideMove = isValidDirection(source, target);

        if (sideMove) {
            return true;
        }

        if (isSameTeam(Team.CHO)) {
            if (target.rowDiff(source) == 1 && target.columnDiff(source) == 0) {
                return true;
            }
        }

        return target.rowDiff(source) == -1 && target.columnDiff(source) == 0;
    }

    private boolean isValidDirection(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (int i = 0; i < dx.size(); i++) {
            if (dx.get(i) == colDiff && dy.get(i) == rowDiff) {
                return true;
            }
        }
        return false;
    }
}
