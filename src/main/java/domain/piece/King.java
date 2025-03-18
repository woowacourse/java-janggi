package domain.piece;

import domain.Team;
import java.util.List;

public class King extends Piece {

    private final List<Move> moves = List.of(Move.FRONT, Move.BACK, Move.RIGHT, Move.LEFT);

    public King(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(int startRow, int startColumn, int targetRow, int targetColumn) {
        for (Move move : moves) {
            int newRow = startRow + move.getDy();
            int newColumn = startColumn + move.getDx();
            if(newRow == targetRow && newColumn == targetColumn) {
                return List.of(move);
            }
        }
        throw new IllegalArgumentException("이 위치로 이동할 수 없습니다.");
    }


}
