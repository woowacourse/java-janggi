package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RowTest {

    @DisplayName("해당 숫자에 맞는 Row를 반환한다.")
    @MethodSource
    @ParameterizedTest
    void testFindRow(int number, Row expected) {
        // given
        // when
        // then
        assertThat(Row.of(number)).isEqualTo(expected);
    }

    private static Stream<Arguments> testFindRow() {
        return Stream.of(
                Arguments.of(0, Row.ZERO),
                Arguments.of(1, Row.ONE),
                Arguments.of(2, Row.TWO),
                Arguments.of(3, Row.THREE),
                Arguments.of(4, Row.FOUR),
                Arguments.of(5, Row.FIVE),
                Arguments.of(6, Row.SIX),
                Arguments.of(7, Row.SEVEN),
                Arguments.of(8, Row.EIGHT)
        );
    }

    @DisplayName("범위가 넘어가는 숫자에 대해서는 예외를 발생한다.")
    @Test
    void testWrongRow() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Row.of(10))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 보드를 벗어난 값입니다.");
    }

    @DisplayName("row에 숫자를 더한 row을 찾는다.")
    @Test
    void testAddRow() {
        // given
        // when
        // then
        assertThat(Row.ONE.add(2)).isEqualTo(Row.THREE);
    }

    @DisplayName("row에 숫자를 더했을 때 범위를 넘어가면 에러를 발생한다.")
    @Test
    void testAddWrongNumber() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Row.FIVE.add(7))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 보드를 벗어난 값입니다.");
    }
}
