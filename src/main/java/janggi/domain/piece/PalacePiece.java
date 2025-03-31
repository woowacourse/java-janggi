package janggi.domain.piece;

import static janggi.domain.movement.Movement.DOWN;
import static janggi.domain.movement.Movement.LEFT;
import static janggi.domain.movement.Movement.LEFT_DOWN;
import static janggi.domain.movement.Movement.LEFT_UP;
import static janggi.domain.movement.Movement.RIGHT;
import static janggi.domain.movement.Movement.RIGHT_DOWN;
import static janggi.domain.movement.Movement.RIGHT_UP;
import static janggi.domain.movement.Movement.UP;

import janggi.domain.game.Board;
import janggi.domain.game.Team;
import janggi.domain.movement.Movement;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Set;

public abstract class PalacePiece extends Piece {

    public PalacePiece(final Team team) {
        super(team);
    }

    @Override
    public void validateMove(final Position source, final Position destination, final Board board) {
        if (!destination.isPalace()) {
            throw new IllegalArgumentException("[ERROR] 궁성 밖으로 이동할 수 없습니다.");
        }
        final List<Position> movablePositions = movements(source).stream()
                .filter(source::canMove)
                .map(source::move)
                .toList();
        if (!movablePositions.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다.");
        }
    }

    private Set<Movement> movements(final Position position) {
        if (position.isPalaceSide()) {
            return Set.of(RIGHT, LEFT, UP, DOWN);
        }
        return Set.of(RIGHT, LEFT, UP, DOWN, RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN);
    }
}
