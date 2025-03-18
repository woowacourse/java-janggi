package domain.piece;

import domain.Team;
import java.util.List;

public class Guard extends Piece{

    public Guard(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(int startRow, int startColumn, int targetRow, int targetColumn) {
        return List.of();
    }

    @Override
    public boolean isCanon() {
        return false;
    }
}
