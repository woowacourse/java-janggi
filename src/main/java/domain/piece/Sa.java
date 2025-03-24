package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;

public class Sa extends Piece {

    private static final List<Move> moves = List.of(Move.FRONT, Move.BACK, Move.RIGHT, Move.LEFT);

    public Sa(Team team) {
        super(team);
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        return moves.stream()
                .filter(startPosition::canMovePosition)
                .map(startPosition::movePosition)
                .filter(newPosition -> newPosition.equals(targetPosition))
                .findFirst()
                .map(position -> List.<Position>of())
                .orElseThrow(() -> new IllegalArgumentException("이 위치로 이동할 수 없습니다."));
    }
}
