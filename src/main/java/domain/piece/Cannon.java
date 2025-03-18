package domain.piece;

import domain.Team;
import java.util.List;

public class Cannon extends Piece{

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(int startRow, int startColumn, int targetRow, int targetColumn) {
        return List.of();
    }

    @Override
    public boolean isCanon() {
        return true;
    }

}
