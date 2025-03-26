package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;

public class Pawn extends Piece {

    private final List<Move> blueTeamMoves = List.of(Move.FRONT, Move.RIGHT, Move.LEFT);
    private final List<Move> redTeamMoves = List.of(Move.BACK, Move.RIGHT, Move.LEFT);

    public Pawn(Team team) {
        super(team);
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        if (this.team == Team.BLUE) {
            return determinePath(startPosition, targetPosition, blueTeamMoves);
        }
        return determinePath(startPosition, targetPosition, redTeamMoves);
    }

    private List<Position> determinePath(Position startPosition, Position targetPosition, List<Move> moves) {
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

    @Override
    public boolean isKing() {
        return false;
    }
}
