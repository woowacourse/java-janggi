package domain.piece;

import domain.Team;
import java.util.List;

public class Pawn extends Piece{

    public Pawn(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(int startRow, int startColumn, int targetRow, int targetColumn) {
        return List.of();
    }
}
