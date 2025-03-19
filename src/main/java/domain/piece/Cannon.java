package domain.piece;

import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(Team team) {
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
        
        return moves;
    }

    @Override
    public boolean isCanon() {
        return true;
    }

}
