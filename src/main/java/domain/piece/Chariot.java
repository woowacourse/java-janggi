package domain.piece;

import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private final List<Move> moves = List.of(Move.FRONT, Move.BACK, Move.RIGHT, Move.LEFT);

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(int startRow, int startColumn, int targetRow, int targetColumn) {

        //1. 세로가 같고 가로가 다르다
        List<Move> moves = new ArrayList<>();
        if (startRow != targetRow && startColumn == targetColumn) {
            int count = targetRow - startRow;
            if (count < 0) {
                for (int i = 0; i < Math.abs(count); i++) {
                    moves.add(Move.FRONT);
                }
            }
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    moves.add(Move.BACK);
                }
            }
        }
        //2. 세로가 다르고 가로가 같다.
        if (startRow == targetRow && startColumn != targetColumn) {
            int count = targetColumn - startColumn;
            if (count < 0) {
                for (int i = 0; i < Math.abs(count); i++) {
                    moves.add(Move.LEFT);
                }
            }
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    moves.add(Move.RIGHT);
                }
            }
        }
        //3. 세로가 같고, 가로가 같다
        if (startRow == targetRow && startColumn == targetColumn) {
            throw new IllegalArgumentException("말을 움직여 주세요");
        }
        if (startRow != targetRow && startColumn != targetColumn) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }
        return moves;
    }

    @Override
    public boolean isCanon() {
        return false;
    }
}