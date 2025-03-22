package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ColumnTest {

    @DisplayName("해당 숫자에 맞는 Column을 반환한다.")
    @MethodSource
    @ParameterizedTest
    void testFindColumn(int number, Column expected) {
        // given
        // when
        // then
        assertThat(Column.of(number)).isEqualTo(expected);
    }

    private static Stream<Arguments> testFindColumn() {
        return Stream.of(
                Arguments.of(0, Column.ZERO),
                Arguments.of(1, Column.ONE),
                Arguments.of(2, Column.TWO),
                Arguments.of(3, Column.THREE),
                Arguments.of(4, Column.FOUR),
                Arguments.of(5, Column.FIVE),
                Arguments.of(6, Column.SIX),
                Arguments.of(7, Column.SEVEN),
                Arguments.of(8, Column.EIGHT)
        );
    }

    @DisplayName("범위가 넘어가는 숫자에 대해서는 예외를 발생한다.")
    @Test
    void testWrongColumn() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Column.of(9))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 보드를 벗어난 값입니다.");
    }

    @DisplayName("column에 숫자를 더한 column을 찾는다.")
    @Test
    void testAddColumn() {
        // given
        // when
        // then
        assertThat(Column.ONE.add(2)).isEqualTo(Column.THREE);
    }

    @DisplayName("column에 숫자를 더했을 때 범위를 넘어가면 에러를 발생한다.")
    @Test
    void testAddWrongNumber() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Column.FIVE.add(7))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 보드를 벗어난 값입니다.");
    }
}
