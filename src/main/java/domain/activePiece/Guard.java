package domain.activePiece;

import domain.Position;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.List;

public class Guard extends ActivePiece {
    private final List<Integer> dx = List.of(-1, 1, 0, 0);
    private final List<Integer> dy = List.of(0, 0, -1, 1);

    public Guard(Team team) {
        super(team, PieceType.SA);
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
}
