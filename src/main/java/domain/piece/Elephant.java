package domain.piece;

import domain.Team;
import java.util.List;

public class Elephant extends Piece{

    private final List<List<Move>> moves = List.of(
            List.of(Move.FRONT,Move.FRONT_LEFT, Move.FRONT_LEFT),
            List.of(Move.FRONT,Move.FRONT_RIGHT, Move.FRONT_RIGHT),
            List.of(Move.BACK,Move.BACK_LEFT, Move.BACK_LEFT),
            List.of(Move.BACK,Move.BACK_RIGHT, Move.BACK_RIGHT),
            List.of(Move.RIGHT,Move.FRONT_RIGHT, Move.FRONT_RIGHT),
            List.of(Move.RIGHT,Move.BACK_RIGHT, Move.BACK_RIGHT),
            List.of(Move.LEFT,Move.FRONT_LEFT, Move.FRONT_LEFT),
            List.of(Move.LEFT,Move.BACK_LEFT, Move.BACK_LEFT)
    );

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(int startRow, int startColumn, int targetRow, int targetColumn) {
        for (List<Move> moveList : moves) {
            boolean compareResult = comparePath(startRow, startColumn, targetRow, targetColumn, moveList);
            if (compareResult) {
                return moveList;
            }
        }
        throw new IllegalArgumentException("이 위치로 이동할 수 없습니다.");
    }

    @Override
    public boolean isCanon() {
        return false;
    }

    private boolean comparePath(int startRow, int startColumn, int targetRow, int targetColumn, List<Move> moveList) {
        int newRow = startRow;
        int newColumn = startColumn;
        for (Move move : moveList) {
            newRow = newRow + move.getDy();
            newColumn = newColumn + move.getDx();
        }
        return newRow == targetRow && newColumn == targetColumn;
    }
}
