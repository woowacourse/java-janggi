package domain.board;

import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DirectionTest {

    @Test
    @DisplayName("(1, 0)을 넣으면 UP을 반환한다.")
    void 좌표에_맞는_올바른_방향을_리턴한다_1() {
        int row = 1;
        int column = 0;

        Direction direction = Direction.from(row, column);

        assertThat(direction).isEqualTo(Direction.UP);
    }

    @Test
    @DisplayName("(1, 1)을 넣으면 UP_RIGTH을 반환한다.")
    void 좌표에_맞는_올바른_방향을_리턴한다_2() {
        int row = 1;
        int column = 1;

        Direction direction = Direction.from(row, column);

        assertThat(direction).isEqualTo(Direction.UP_RIGHT);
    }

    @Test
    @DisplayName("(0, 1)을 넣으면 RIGHT를 반환한다.")
    void from_좌표에_맞는_올바른_방향을_리턴한다_3() {
        int row = 0;
        int column = 1;

        Direction direction = Direction.from(row, column);

        assertThat(direction).isEqualTo(Direction.RIGHT);
    }

    @Test
    @DisplayName("(-1, 1)을 넣으면 DOWN_RIGHT를 반환한다.")
    void 좌표에_맞는_올바른_방향을_리턴한다_4() {
        int row = -1;
        int column = 1;

        Direction direction = Direction.from(row, column);

        assertThat(direction).isEqualTo(Direction.DOWN_RIGHT);
    }

    @Test
    @DisplayName("(-1, 0)을 넣으면 DOWN을 반환한다.")
    void 좌표에_맞는_올바른_방향을_리턴한다_5() {
        int row = -1;
        int column = 0;

        Direction direction = Direction.from(row, column);

        assertThat(direction).isEqualTo(Direction.DOWN);
    }

    @Test
    @DisplayName("(-1, -1)을 넣으면 DOWN_LEFT를 반환한다.")
    void 좌표에_맞는_올바른_방향을_리턴한다_6() {
        int row = -1;
        int column = -1;

        Direction direction = Direction.from(row, column);

        assertThat(direction).isEqualTo(Direction.DOWN_LEFT);
    }

    @Test
    @DisplayName("(0, -1)을 넣으면 LEFT를 반환한다.")
    void from_좌표에_맞는_올바른_방향을_리턴한다_7() {
        int row = 0;
        int column = -1;

        Direction direction = Direction.from(row, column);

        assertThat(direction).isEqualTo(Direction.LEFT);
    }

    @Test
    @DisplayName("(1, -1)을 넣으면 UP_LEFT를 반환한다.")
    void 좌표에_맞는_올바른_방향을_리턴한다_8() {
        int row = 1;
        int column = -1;

        Direction direction = Direction.from(row, column);

        assertThat(direction).isEqualTo(Direction.UP_LEFT);
    }

    @Test
    @DisplayName("(2, 2)를 넣으면 예외가 발생한다.")
    void 단위방향에_맞지_않는_값을_넣으면_예외가_발생한다() {
        int row = 2;
        int column = 2;

        assertThatThrownBy(() -> Direction.from(row, column))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("(5,5), (7,5)를 넣으면 UP, UP을 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_1() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(7, 5);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.UP);
        assertThat(directions.poll()).isEqualTo(Direction.UP);
    }

    @Test
    @DisplayName("(5,5), (3,5)를 넣으면 DOWN, DOWN을 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_2() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(3, 5);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN);
    }

    @Test
    @DisplayName("(5,5), (5,3)를 넣으면 LEFT, LEFT을 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_3() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(5, 3);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.LEFT);
        assertThat(directions.poll()).isEqualTo(Direction.LEFT);
    }

    @Test
    @DisplayName("(5,5), (5,7)를 넣으면 RIGHT, RIGHT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_4() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(5, 7);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.RIGHT);
        assertThat(directions.poll()).isEqualTo(Direction.RIGHT);
    }


    @Test
    @DisplayName("(5,5), (7,6)를 넣으면 UP, UP_RIGHT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_5() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(7, 6);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.UP);
        assertThat(directions.poll()).isEqualTo(Direction.UP_RIGHT);
    }

    @Test
    @DisplayName("(5,5), (7,4)를 넣으면 UP, UP_LEFT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_6() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(7, 4);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.UP);
        assertThat(directions.poll()).isEqualTo(Direction.UP_LEFT);
    }


    @Test
    @DisplayName("(5,5), (6,7)를 넣으면 RIGHT, UP_RIGHT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_7() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(6, 7);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.RIGHT);
        assertThat(directions.poll()).isEqualTo(Direction.UP_RIGHT);
    }


    @Test
    @DisplayName("(5,5), (4,7)를 넣으면 RIGHT, DOWN_RIGHT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_8() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(4, 7);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.RIGHT);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_RIGHT);
    }

    @Test
    @DisplayName("(5,5), (3,6)를 넣으면 DOWN, DOWN_RIGHT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_9() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(3, 6);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_RIGHT);
    }

    @Test
    @DisplayName("(5,5), (3,4)를 넣으면 DOWN, DOWN_LEFT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_10() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(3, 4);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_LEFT);
    }

    @Test
    @DisplayName("(5,5), (6,3)를 넣으면 LEFT, UP_LEFT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_11() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(6, 3);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.LEFT);
        assertThat(directions.poll()).isEqualTo(Direction.UP_LEFT);
    }

    @Test
    @DisplayName("(5,5), (4,3)를 넣으면 LEFT, DOWN_LEFT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_12() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(4, 3);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(2);
        assertThat(directions.poll()).isEqualTo(Direction.LEFT);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_LEFT);
    }

    @Test
    @DisplayName("(5,5), (8,7)를 넣으면 UP, UP_RIGHT, UP_RIGHT  반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_13() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(8, 7);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(3);
        assertThat(directions.poll()).isEqualTo(Direction.UP);
        assertThat(directions.poll()).isEqualTo(Direction.UP_RIGHT);
        assertThat(directions.poll()).isEqualTo(Direction.UP_RIGHT);
    }

    @Test
    @DisplayName("(5,5), (8,3)를 넣으면 UP, UP_LEFT, UP_LEFT  반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_14() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(8, 3);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(3);
        assertThat(directions.poll()).isEqualTo(Direction.UP);
        assertThat(directions.poll()).isEqualTo(Direction.UP_LEFT);
        assertThat(directions.poll()).isEqualTo(Direction.UP_LEFT);
    }

    @Test
    @DisplayName("(5,5), (7,8)를 넣으면 RIGHT, UP_RIGHT, UP_RIGHT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_15() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(7, 8);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(3);
        assertThat(directions.poll()).isEqualTo(Direction.RIGHT);
        assertThat(directions.poll()).isEqualTo(Direction.UP_RIGHT);
        assertThat(directions.poll()).isEqualTo(Direction.UP_RIGHT);
    }

    @Test
    @DisplayName("(5,5), (3,8)를 넣으면 RIGHT, DOWN_RIGHT, DOWN_RIGHT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_16() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(3, 8);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(3);
        assertThat(directions.poll()).isEqualTo(Direction.RIGHT);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_RIGHT);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_RIGHT);
    }

    @Test
    @DisplayName("(5,5), (2,7)를 넣으면 DOWN, DOWN_RIGHT, DOWN_RIGHT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_17() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(2, 7);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(3);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_RIGHT);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_RIGHT);
    }

    @Test
    @DisplayName("(5,5), (2,3)를 넣으면 DOWN, DOWN_LEFT, DOWN_LEFT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_18() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(2, 3);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(3);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_LEFT);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_LEFT);
    }

    @Test
    @DisplayName("(5,5), (7,2)를 넣으면 LEFT, UP_LEFT, UP_LEFT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_19() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(7, 2);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(3);
        assertThat(directions.poll()).isEqualTo(Direction.LEFT);
        assertThat(directions.poll()).isEqualTo(Direction.UP_LEFT);
        assertThat(directions.poll()).isEqualTo(Direction.UP_LEFT);
    }

    @Test
    @DisplayName("(5,5), (3,2)를 넣으면 LEFT, DOWN_LEFT, DOWN_LEFT 반환한다")
    void 시작_도착_좌표에_맞는_올바른_방향을_리턴한다_20() {
        Position startPosition = Position.of(5, 5);
        Position endPosition = Position.of(3, 2);

        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        assertThat(directions.size()).isEqualTo(3);
        assertThat(directions.poll()).isEqualTo(Direction.LEFT);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_LEFT);
        assertThat(directions.poll()).isEqualTo(Direction.DOWN_LEFT);
    }
}
