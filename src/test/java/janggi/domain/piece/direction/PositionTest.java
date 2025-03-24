package janggi.domain.piece.direction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @DisplayName("같은 위치면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "1, 1, 1, 1, true", " 1, 1, 2, 2, false"
    })
    void equalTest(final int firstX, final int firstY, final int secondX, final int secondY, final boolean expected) {

        // given
        final Position firstPosition = new Position(firstX, firstY);
        final Position secondPosition = new Position(secondX, secondY);

        // when & then
        assertThat(firstPosition.equals(secondPosition)).isEqualTo(expected);
    }

    @DisplayName("포지션의 유효 범위를 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "-1, 1", "9, 1", "1, -1", "2, 10"
    })
    void validateRangeTest(final int x, final int y) {

        // given

        // when & then
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드를 벗어났습니다.");
    }

    @DisplayName("해당 위치로 이동 가능하다면 true를 반환한다.")
    @Test
    void canMoveTest() {

        // given
        final Position position = new Position(0, 0);

        // when
        final boolean result = position.canMove(Direction.RIGHT_UP);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("해당 위치로 이동 불가능하다면 false를 반환한다.")
    @Test
    void canNotMoveTest() {

        // given
        final Position position = new Position(0, 0);

        // when
        final boolean result = position.canMove(Direction.LEFT);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("해당 위치로 이동하면 새로운 포지션을 갖는다.")
    @Test
    void moveTest() {

        // given
        final Position position = new Position(0, 0);

        // when
        final Position newPosition = position.move(Direction.RIGHT_UP);

        // then
        assertThat(newPosition.equals(new Position(1, 1))).isTrue();
    }

    @DisplayName("주어진 좌표가 보드 안이면 true 아니면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "0, 0, true", "8, 9, true", "-1, -1, false", "9, 8, false"
    })
    void isInBoardTest(final int x, final int y, final boolean expected) {

        // given
        final Position position = new Position(0, 0);

        // when & then
        assertThat(position.isInBoard(x, y)).isEqualTo(expected);
    }
}
