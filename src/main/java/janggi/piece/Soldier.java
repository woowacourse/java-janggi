package janggi.piece;

import static janggi.movement.Movement.DOWN;
import static janggi.movement.Movement.LEFT;
import static janggi.movement.Movement.RIGHT;
import static janggi.movement.Movement.UP;

import janggi.game.Board;
import janggi.position.Position;
import janggi.game.Team;
import janggi.movement.Movement;
import java.util.List;
import java.util.Set;

public final class Soldier implements Piece {

    private final Team team;

    public Soldier(final Team team) {
        this.team = team;
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

    @Override
    public Team team() {
        return team;
    }
}
