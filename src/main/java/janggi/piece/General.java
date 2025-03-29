package janggi.piece;

import static janggi.movement.Movement.DOWN;
import static janggi.movement.Movement.LEFT;
import static janggi.movement.Movement.LEFT_DOWN;
import static janggi.movement.Movement.LEFT_UP;
import static janggi.movement.Movement.RIGHT;
import static janggi.movement.Movement.RIGHT_DOWN;
import static janggi.movement.Movement.RIGHT_UP;
import static janggi.movement.Movement.UP;

import janggi.game.Board;
import janggi.game.Team;
import janggi.movement.Movement;
import janggi.position.Position;
import java.util.List;
import java.util.Set;

public final class General implements Piece {

    private final Team team;

    public General(final Team team) {
        this.team = team;
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

    @Override
    public int point() {
        return 0;
    }


    private Set<Movement> movements(final Position position) {
        if (position.isPalaceSide()) {
            return Set.of(RIGHT, LEFT, UP, DOWN);
        }
        return Set.of(RIGHT, LEFT, UP, DOWN, RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN);
    }

    @Override
    public Type type() {
        return Type.GENERAL;
    }

    @Override
    public Team team() {
        return team;
    }
}
