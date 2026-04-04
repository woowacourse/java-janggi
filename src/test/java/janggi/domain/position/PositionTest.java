package janggi.domain.position;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PositionTest {

    @ParameterizedTest(name = "문자열={0}, 행 좌표={1}, 열 좌표={2}")
    @CsvSource({
            "09, 10, 9",
            "11, 1, 1",
            "19, 1, 9"
    })
    void 문자열로_위치를_생성하면_올바른_좌표를_가진다(String input, int expectedRow, int expectedCol) {
        // when
        Position position = Position.from(input);

        // then
        assertAll(
                () -> assertThat(position.getRowValue()).isEqualTo(expectedRow),
                () -> assertThat(position.getColumnValue()).isEqualTo(expectedCol)
        );
    }

    @ParameterizedTest(name = "행={0}, 열={1}")
    @CsvSource({
            "1, 1",
            "10, 9"
    })
    void 숫자로_위치를_생성하면_올바른_좌표를_가진다(int row, int col) {
        // when
        Position position = Position.of(row, col);

        // then
        assertAll(
                () -> assertThat(position.getRowValue()).isEqualTo(row),
                () -> assertThat(position.getColumnValue()).isEqualTo(col)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"105", "1"})
    void 두_자리가_아닌_문자열로_위치를_생성하면_예외가_발생한다(String input) {
        // when & then
        assertThatThrownBy(() -> Position.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바른 좌표값이 아닙니다.");
    }

    @Test
    void 범위를_벗어난_열좌표로_위치를_생성하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> Position.from("10"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 열 좌표는 1~9까지 사용 가능 합니다");
    }

    @Test
    void 범위를_벗어난_행좌표로_위치를_생성하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> Position.from("a0"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 행 좌표는 1~10까지 사용 가능 합니다");
    }

    @Test
    void 동일한_좌표를_가진_위치_객체는_동등하다() {
        // given
        Position position1 = Position.from("12");
        Position position2 = Position.from("12");


        assertThat(position1).isEqualTo(position2);
    }

    @Test
    void 직선_방향으로_다음_좌표를_반환한다() {
        Position from = Position.of(1, 1);
        Position to = Position.of(3, 1);
        Position next = from.nextStraight(to);

        // when & then
        assertThat(next).isEqualTo(Position.of(2, 1));
    }

    @Test
    void 대각선_방향으로_다음_좌표를_반환한다() {
        // given
        Position from = Position.of(1, 1);
        Position to = Position.of(3, 3);

        // when
        Position next = from.nextDiagonal(to);

        // then
        assertThat(next).isEqualTo(Position.of(2, 2));
    }
}
