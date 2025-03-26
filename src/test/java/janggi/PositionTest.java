package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.position.Direction;
import janggi.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PositionTest {

    @ParameterizedTest
    @DisplayName("장기판 밖의 위치를 지정할 수 없다.")
    @CsvSource(value = {"0,1", "1, 0", "10, 1", "1, 11"})
    void shouldThrowExceptionWhenOutsideOfBoard(int x, int y) {
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가로, 세로 방향이 주어졌을 때 방향으로 이동한 Position을 반환한다.")
    void should_return_position_when_offset_given_direction() {
        // given
        Position position = new Position(5, 5);

        // when
        Position up = position.move(Direction.UP);
        Position down = position.move(Direction.DOWN);
        Position left = position.move(Direction.LEFT);
        Position right = position.move(Direction.RIGHT);

        // then
        assertAll(
                () -> assertThat(up).isEqualTo(new Position(5, 4)),
                () -> assertThat(down).isEqualTo(new Position(5, 6)),
                () -> assertThat(left).isEqualTo(new Position(4, 5)),
                () -> assertThat(right).isEqualTo(new Position(6, 5))
        );
    }
}
