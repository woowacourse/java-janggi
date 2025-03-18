package domain.piece;

import domain.Team;
import java.util.List;

public class Chariot extends Piece{

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(int startRow, int startColumn, int targetRow, int targetColumn) {
        return List.of();
    }
}
