package janggi.piece;

import static janggi.piece.direction.Direction.DOWN;
import static janggi.piece.direction.Direction.LEFT;
import static janggi.piece.direction.Direction.RIGHT;
import static janggi.piece.direction.Direction.UP;

import janggi.piece.direction.Movement;
import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {

    private static final List<Movement> CHO_MOVEMENTS = List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT)
    );
    private static final List<Movement> HAN_MOVEMENTS = List.of(
            new Movement(DOWN), new Movement(RIGHT), new Movement(LEFT)
    );

    public Soldier(final Team team) {
        super(PieceType.SOLDIER, team);
    }

    @Override
    protected void validatePath(final Map<Position, Piece> pieces, final Path path) {
        if (hasPieceInMiddle(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Movement> getMovements() {
        if (isSameTeam(Team.CHO)) {
            return CHO_MOVEMENTS;
        }
        return HAN_MOVEMENTS;
    }
}
