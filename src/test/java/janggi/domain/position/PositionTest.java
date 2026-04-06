package janggi.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionTest {

    @DisplayName("정해진 범위가 벗어난 position을 찾을 경우 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({
            "-1, -1",
            "10, 9"
    })
    void of_OutOfRangePosition_ThrowException(int row, int column) {
        assertThatThrownBy(() -> Position.of(row, column))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 정해진_범위_내로_움직이면_올바른_Position_객체를_반환한다() {
        Position current = Position.of(4, 4);
        Optional<Position> move = current.move(Direction.UP);
        Position position = move.get();

        assertThat(position).isEqualTo(Position.of(5, 4));
    }

    @Test
    void 정해진_범위_내로_움직이지_않으면_Optinal_null_객체를_반환한다() {
        Position current = Position.of(9, 8);
        Optional<Position> move = current.move(Direction.UP);

        assertThat(move).isEmpty();
    }
}
