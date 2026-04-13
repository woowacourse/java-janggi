package domain.path;

import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DirectionTest {
    private Position departure;

    @BeforeEach
    void setUp() {
        departure = new Position(5, 5);
    }

    @Test
    void 왼쪽_방향을_판정한다() {
        Position destination = new Position(4, 5);
        Direction direction = Direction.decideDirection(departure.calculateDeltaX(destination), departure.calculateDeltaY(destination));
        assertThat(direction).isEqualTo(Direction.LEFT);
    }

    @Test
    void 오른쪽_방향을_판정한다() {
        Position destination = new Position(6, 5);
        Direction direction = Direction.decideDirection(departure.calculateDeltaX(destination), departure.calculateDeltaY(destination));
        assertThat(direction).isEqualTo(Direction.RIGHT);
    }

    @Test
    void 위쪽_방향을_판정한다() {
        Position destination = new Position(5, 6);
        Direction direction = Direction.decideDirection(departure.calculateDeltaX(destination), departure.calculateDeltaY(destination));
        assertThat(direction).isEqualTo(Direction.UP);
    }

    @Test
    void 아래쪽_방향을_판정한다() {
        Position destination = new Position(5, 4);
        Direction direction = Direction.decideDirection(departure.calculateDeltaX(destination), departure.calculateDeltaY(destination));
        assertThat(direction).isEqualTo(Direction.DOWN);
    }

    @Test
    void 북동쪽_방향을_판정한다() {
        Position destination = new Position(6, 6);
        Direction direction = Direction.decideDirection(departure.calculateDeltaX(destination), departure.calculateDeltaY(destination));
        assertThat(direction).isEqualTo(Direction.NORTHEAST);
    }

    @Test
    void 북서쪽_방향을_판정한다() {
        Position destination = new Position(4, 6);
        Direction direction = Direction.decideDirection(departure.calculateDeltaX(destination), departure.calculateDeltaY(destination));
        assertThat(direction).isEqualTo(Direction.NORTHWEST);
    }

    @Test
    void 남동쪽_방향을_판정한다() {
        Position destination = new Position(6, 4);
        Direction direction = Direction.decideDirection(departure.calculateDeltaX(destination), departure.calculateDeltaY(destination));
        assertThat(direction).isEqualTo(Direction.SOUTHEAST);
    }

    @Test
    void 남서쪽_방향을_판정한다() {
        Position destination = new Position(4, 4);
        Direction direction = Direction.decideDirection(departure.calculateDeltaX(destination), departure.calculateDeltaY(destination));
        assertThat(direction).isEqualTo(Direction.SOUTHWEST);
    }

    @Test
    void 직선_방향인지_반환한다() {
        assertThat(Direction.isLinear(Direction.UP)).isTrue();
        assertThat(Direction.isLinear(Direction.SOUTHWEST)).isFalse();
    }

    @Test
    void 출발_위치와_도착_위치가_같으면_예외를_던진다() {
        assertThatThrownBy(() ->
                Direction.decideDirection(departure.calculateDeltaX(departure),
                        departure.calculateDeltaY(departure))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 직선_방향과_대각선_방향_모두_아닐_경우_예외를_던진다() {
        Position destination = new Position(2, 3);
        assertThatThrownBy(() ->
                Direction.decideDirection(departure.calculateDeltaX(destination),
                        departure.calculateDeltaY(destination))).isInstanceOf(IllegalArgumentException.class);

    }
}
