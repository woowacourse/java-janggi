package domain.piece.strategy;

import domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DirectionTest {
    private static final Position LEFT_UP = Position.of(1, 3);
    private static final Position UP = Position.of(1, 5);
    private static final Position RIGHT_UP = Position.of(1, 7);
    private static final Position LEFT = Position.of(3, 3);
    private static final Position CENTER = Position.of(3, 5);
    private static final Position RIGHT = Position.of(3, 7);
    private static final Position LEFT_DOWN = Position.of(5, 3);
    private static final Position DOWN = Position.of(5, 5);
    private static final Position RIGHT_DOWN = Position.of(5, 7);

    @Test
    void 왼쪽_위로_가면_LEFT_UP_이_반횐되어야_한다() {
        Assertions.assertThat(Direction.getDirectionByPosition(CENTER, LEFT_UP)).isEqualTo(Direction.LEFT_UP);
    }

    @Test
    void 위로_가면_UP_이_반횐되어야_한다() {
        Assertions.assertThat(Direction.getDirectionByPosition(CENTER, UP)).isEqualTo(Direction.UP);
    }

    @Test
    void 오른쪽_위로_가면_RIGHT_UP_이_반횐되어야_한다() {
        Assertions.assertThat(Direction.getDirectionByPosition(CENTER, RIGHT_UP)).isEqualTo(Direction.RIGHT_UP);
    }

    @Test
    void 왼쪽으로_가면_LEFT_이_반횐되어야_한다() {
        Assertions.assertThat(Direction.getDirectionByPosition(CENTER, LEFT)).isEqualTo(Direction.LEFT);
    }

    @Test
    void 오른쪾으로_가면_RIGHT_이_반횐되어야_한다() {
        Assertions.assertThat(Direction.getDirectionByPosition(CENTER, RIGHT)).isEqualTo(Direction.RIGHT);
    }

    @Test
    void 왼쪽_아래로_가면_LEFT_DOWN_이_반횐되어야_한다() {
        Assertions.assertThat(Direction.getDirectionByPosition(CENTER, LEFT_DOWN)).isEqualTo(Direction.LEFT_DOWN);
    }

    @Test
    void 아래로_가면_DOWN_이_반횐되어야_한다() {
        Assertions.assertThat(Direction.getDirectionByPosition(CENTER, DOWN)).isEqualTo(Direction.DOWN);
    }

    @Test
    void 오른쪽_아래로_가면_RIGHT_DOWN_이_반횐되어야_한다() {
        Assertions.assertThat(Direction.getDirectionByPosition(CENTER, RIGHT_DOWN)).isEqualTo(Direction.RIGHT_DOWN);
    }
}