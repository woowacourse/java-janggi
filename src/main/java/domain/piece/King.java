package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;

public class King extends Piece {

    private final List<Move> moves = List.of(Move.FRONT, Move.BACK, Move.RIGHT, Move.LEFT);

    public King(Team team) {
        super(team);
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        for (Move move : moves) {
            if (!startPosition.canMovePosition(move)) {
                continue;
            }
            Position newPosition = startPosition.movePosition(move);
            if (newPosition.equals(targetPosition)) {
                return List.of();
            }
        }
        throw new IllegalArgumentException("이 위치로 이동할 수 없습니다.");
    }

    @Override
    public boolean isCanon() {
        return false;
    }
}
