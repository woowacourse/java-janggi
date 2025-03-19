package domain.piece;

import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private final List<Move> moves = List.of(Move.FRONT, Move.BACK, Move.RIGHT, Move.LEFT);

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(Position startPosition, Position targetPosition) {

        //1. 세로가 다르고 가로가 같다.
        List<Move> moves = new ArrayList<>();
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) == 0) {
            int count = startPosition.compareRow(targetPosition);
            if (count < 0) {
                for (int i = 0; i < Math.abs(count); i++) {
                    moves.add(Move.BACK);
                }
            }
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    moves.add(Move.FRONT);
                }
            }
        }
        //2. 세로가 같고 가로가 다르다
        if (startPosition.compareRow(targetPosition) == 0 && startPosition.compareColumn(targetPosition) != 0) {
            int count = startPosition.compareColumn(targetPosition);
            if (count < 0) {
                for (int i = 0; i < Math.abs(count); i++) {
                    moves.add(Move.RIGHT);
                }
            }
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    moves.add(Move.LEFT);
                }
            }
        }
        //3. 세로가 같고, 가로가 같다
        if (startPosition.equals(targetPosition)) {
            throw new IllegalArgumentException("말을 움직여 주세요");
        }
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) != 0) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }
        return moves;
    }

    @Override
    public boolean isCanon() {
        return false;
    }
}