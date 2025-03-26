package domain.piece;

import domain.Position;
import domain.Team;
import domain.move.Moves;
import java.util.List;

public abstract class FixedMovePiece extends Piece {

    public FixedMovePiece(Team team) {
        super(team);
    }

    public abstract List<Moves> getMoveList();

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        List<Moves> movesList = getMoveList();
        for (Moves moves : movesList) {
            boolean compareResult = moves.comparePath(startPosition, targetPosition);
            if (compareResult) {
                return moves.convertToPath(startPosition);
            }
        }
        throw new IllegalArgumentException("이 위치로 이동할 수 없습니다.");
    }
}
