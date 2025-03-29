package janggi.temp.piece;

import static janggi.temp.movement.Movement.DOWN;
import static janggi.temp.movement.Movement.LEFT;
import static janggi.temp.movement.Movement.LEFT_DOWN;
import static janggi.temp.movement.Movement.LEFT_UP;
import static janggi.temp.movement.Movement.RIGHT;
import static janggi.temp.movement.Movement.RIGHT_DOWN;
import static janggi.temp.movement.Movement.RIGHT_UP;
import static janggi.temp.movement.Movement.UP;

import janggi.temp.Board;
import janggi.temp.Team;
import janggi.temp.movement.Movement;
import janggi.temp.position.Position;
import java.util.List;
import java.util.Set;

public final class Guard extends Piece {

    public Guard(final Team team) {
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

    @Override
    public Type type() {
        return Type.GUARD;
    }
}
