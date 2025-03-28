package janggi.board;

import janggi.domain.board.Position;
import janggi.domain.move.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PositionTest {

    @Test
    @DisplayName("좌표 이동 테스트")
    void test1() {
        Position position = new Position(1, 1);
        Position movedPosition = position.move(Direction.RIGHT);

        assertThat(movedPosition).isEqualTo(new Position(2, 1));
    }

    @Test
    @DisplayName("좌표 이동 범위 테스트")
    void test2() {
        Position position = new Position(0, 0);
        Position movedPosition = position.move(Direction.UP);

        assertAll(
                () -> assertThat(position.isInBoardRange()).isTrue(),
                () -> assertThat(movedPosition.isInBoardRange()).isFalse()
        );
    }

    @Test
    @DisplayName("궁성에 속하는 포지션인지 반환 테스트 - true")
    void test3() {
        Position position = new Position(3, 0);

        assertThat(position.isDiagonalMovable()).isTrue();
    }

    @Test
    @DisplayName("궁성에 속하는 포지션인지 반환 테스트 - false")
    void test4() {
        Position position = new Position(0, 0);

        assertThat(position.isDiagonalMovable()).isFalse();
    }
}
