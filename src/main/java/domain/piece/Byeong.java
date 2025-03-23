package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;

public class Byeong extends Piece {

    private static final List<Move> moves = List.of(Move.BACK, Move.RIGHT, Move.LEFT);

    public Byeong(Team team) {
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
    public boolean isPo() {
        return false;
    }

    @Override
    public boolean isGung() {
        return false;
    }
}
