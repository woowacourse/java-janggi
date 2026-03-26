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
            if (target.rowDiff(source) == 0 && target.columnDiff(source) == 1) {
                return true;
            }
        }

        return target.rowDiff(source) == 0 && target.columnDiff(source) == -1;
    }

    private boolean isValidDirection(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        for (int i = 0; i < dx.size(); i++) {
            if (dx.get(i) == rowDiff && dy.get(i) == colDiff) {
                return true;
            }
        }
        return false;
    }
}
