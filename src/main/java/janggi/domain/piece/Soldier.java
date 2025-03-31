package janggi.domain.piece;

import static janggi.domain.movement.Movement.DOWN;
import static janggi.domain.movement.Movement.LEFT;
import static janggi.domain.movement.Movement.RIGHT;
import static janggi.domain.movement.Movement.UP;

import janggi.domain.game.Board;
import janggi.domain.game.Team;
import janggi.domain.movement.Movement;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Set;

public final class Soldier extends Piece {

    public Soldier(final Team team) {
        super(team);
    }

    @Override
    public void validateMove(final Position source, final Position destination, final Board board) {
        final List<Position> movablePositions = movements().stream()
                .filter(source::canMove)
                .map(source::move)
                .toList();
        if (!movablePositions.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다.");
        }
    }

    @Override
    public int point() {
        return 2;
    }

    private Set<Movement> movements() {
        if (team() == Team.HAN) {
            return Set.of(DOWN, RIGHT, LEFT);
        }
        return Set.of(UP, RIGHT, LEFT);
    }

    @Override
    public Type type() {
        return Type.SOLDIER;
    }
}
