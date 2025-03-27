package janggi.temp;

import static janggi.temp.Movement.DOWN;
import static janggi.temp.Movement.LEFT;
import static janggi.temp.Movement.RIGHT;
import static janggi.temp.Movement.UP;

import java.util.Objects;
import java.util.Set;

public final class Soldier {

    private final Position position;
    private final Team team;

    public Soldier(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    public Soldier move(final Position destination) {
        if (destination.equals(position)) {
            throw new IllegalArgumentException("[ERROR] 본인의 위치로는 이동할 수 없습니다.");
        }
        for (Movement movement : movements()) {
            if (position.canMove(movement)) {
                if (position.move(movement).equals(destination)) {
                    return new Soldier(destination, team);
                }
            }
        }
        throw new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다.");
    }

    private Set<Movement> movements() {
        if (team == Team.HAN) {
            return Set.of(DOWN, RIGHT, LEFT);
        }
        return Set.of(UP, RIGHT, LEFT);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Soldier soldier = (Soldier) o;
        return Objects.equals(position, soldier.position) && team == soldier.team;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(position);
        result = 31 * result + Objects.hashCode(team);
        return result;
    }
}
