package janggi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JanggiPositionTest {

    @DisplayName("정해진 범위가 벗어난 position을 찾을 경우 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({
            "-1, -1",
            "10, 9"
    })
    void of_OutOfRangePosition_ThrowException(int row, int column) {
        assertThatThrownBy(() -> JanggiPosition.of(row, column))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 좌표입니다.");
    }

    @Test
    void 정해진_범위_내로_움직이면_올바른_Position_객체를_반환한다() {
        JanggiPosition current = JanggiPosition.of(4, 4);
        Optional<JanggiPosition> move = current.move(3, 3);
        JanggiPosition position = move.get();

        assertThat(position).isEqualTo(JanggiPosition.of(7, 7));
    }

    @Test
    void 정해진_범위_내로_움직이지_않으면_Optinal_null_객체를_반환한다() {
        JanggiPosition current = JanggiPosition.of(4, 4);
        Optional<JanggiPosition> move = current.move(5, 5);

        assertThat(move).isEmpty();
    }

    @DisplayName("해당 포지션이 궁성의 대각에 위치하면 true를 반환한다")
    @ParameterizedTest
    @CsvSource({
            "0, 3",
            "0, 5",
            "2, 3",
            "2, 5",
            "7, 3",
            "7, 5",
            "9, 3",
            "9, 5",
    })
    void isPalaceDiagonal_LocatedAtDiagonal_ReturnTrue(int row, int col) {
        JanggiPosition position = JanggiPosition.of(row, col);

        assertThat(position.isPalaceDiagonal()).isTrue();
    }

    @DisplayName("해당 포지션이 궁성의 직교에 위치하면 false를 반환한다")
    @ParameterizedTest
    @CsvSource({
            "0, 4",
            "1, 3",
            "1, 5",
            "2, 4",
            "7, 4",
            "8, 3",
            "8, 5",
            "9, 4",
    })
    void isPalaceDiagonal_LocatedAtOrthogonal_ReturnFalse(int row, int col) {
        JanggiPosition position = JanggiPosition.of(row, col);

        assertThat(position.isPalaceDiagonal()).isFalse();
    }

    @DisplayName("해당 포지션이 궁성 밖에 위치하면 false를 반환한다")
    @ParameterizedTest
    @CsvSource({
            "0, 2",
            "0, 6",
            "2, 2",
            "2, 6",
            "6, 3",
            "6, 5",
            "9, 2",
            "9, 6",
    })
    void isPalaceDiagonal_LocatedOutside_ReturnFalse(int row, int col) {
        JanggiPosition position = JanggiPosition.of(row, col);

        assertThat(position.isPalaceDiagonal()).isFalse();
    }
}
