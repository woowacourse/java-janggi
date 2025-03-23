package domain.piece;

import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Po extends Piece {

    public Po(Team team) {
        super(team);
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        validateStraightMove(startPosition, targetPosition);
        List<Move> moves = decideMove(startPosition, targetPosition);
        return convertToPath(moves, startPosition);
    }

    private void validateStraightMove(Position startPosition, Position targetPosition) {
        if (startPosition.compareRow(targetPosition) != 0 && startPosition.compareColumn(targetPosition) != 0) {
            throw new IllegalArgumentException("이 위치로는 움직일 수 없습니다.");
        }
    }

    private List<Move> decideMove(Position startPosition, Position targetPosition) {
        int rowDiff = startPosition.compareRow(targetPosition);
        int columnDiff = startPosition.compareColumn(targetPosition);
        if (rowDiff > 0) {
            return new ArrayList<>(Collections.nCopies(rowDiff, Move.FRONT));
        }
        if (rowDiff < 0) {
            return new ArrayList<>(Collections.nCopies(Math.abs(rowDiff), Move.BACK));
        }
        if (columnDiff < 0) {
            return new ArrayList<>(Collections.nCopies(Math.abs(columnDiff), Move.RIGHT));
        }
        return new ArrayList<>(Collections.nCopies(columnDiff, Move.LEFT));
    }

    @Override
    public boolean isPo() {
        return true;
    }

}
