package domain.piece;

import domain.Team;
import java.util.List;

public class Pawn extends Piece{

    private final List<Move> moves = List.of(Move.FRONT, Move.BACK, Move.RIGHT, Move.LEFT);

    public Pawn(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(int startRow, int startColumn, int targetRow, int targetColumn) {
        return List.of();
    }
}
