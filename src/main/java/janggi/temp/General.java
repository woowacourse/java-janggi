package janggi.temp;

import static janggi.temp.Movement.DOWN;
import static janggi.temp.Movement.LEFT;
import static janggi.temp.Movement.LEFT_DOWN;
import static janggi.temp.Movement.LEFT_UP;
import static janggi.temp.Movement.RIGHT;
import static janggi.temp.Movement.RIGHT_DOWN;
import static janggi.temp.Movement.RIGHT_UP;
import static janggi.temp.Movement.UP;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class General {

    private final Position position;
    private final Team team;

    public General(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    public General move(final Position destination) {
        if (destination.equals(position)) {
            throw new IllegalArgumentException("[ERROR] 본인의 위치로는 이동할 수 없습니다.");
        }
        if (!destination.isPalace()) {
            throw new IllegalArgumentException("[ERROR] 궁은 궁성 밖으로 이동할 수 없습니다.");
        }
        for (Movement movement : movements()) {
            if (position.canMove(movement)) {
                if (position.move(movement).equals(destination)) {
                    return new General(destination, team);
                }
            }
        }
        throw new IllegalArgumentException("[ERROR] 궁의 규칙에 어긋나는 움직입입니다.");
    }

    private Set<Movement> movements() {
        List<Position> hanPalaceSides = List.of(new Position(Column.THREE, Row.ONE),
                new Position(Column.FOUR, Row.ZERO), new Position(Column.FIVE, Row.ONE),
                new Position(Column.FOUR, Row.TWO));
        List<Position> choPalaceSides = List.of(new Position(Column.THREE, Row.EIGHT),
                new Position(Column.FOUR, Row.SEVEN), new Position(Column.FIVE, Row.EIGHT),
                new Position(Column.FOUR, Row.NINE));
        if (hanPalaceSides.contains(position) || choPalaceSides.contains(position)) {
            return Set.of(RIGHT, LEFT, UP, DOWN);
        }
        return Set.of(RIGHT, LEFT, UP, DOWN, RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final General general = (General) o;
        return Objects.equals(position, general.position) && team == general.team;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(position);
        result = 31 * result + Objects.hashCode(team);
        return result;
    }
}
