package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Fixtures;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalaceTest {

    @DisplayName("해당 좌표가 궁상인지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideIsInPalaceData")
    void isInPalace(Point point, boolean expected) {
        //given & when
        boolean result = Palace.isInPalace(point);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("두 개의 좌표 모두 같은 궁성인지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideIsSamePalaceData")
    void isSamePalace(Point point1, Point point2, boolean expected) {
        //given & when
        boolean result = Palace.isSamePalace(point1, point2);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("대각선으로 이동할 수 있는 궁성 좌표인지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideCanMoveDiagonalData")
    void canMoveDiagonal(Point point1, Point point2, boolean expected) {
        //given & when
        boolean result = Palace.canMoveDiagonal(point1, point2);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideIsInPalaceData() {
        return Stream.of(
                Arguments.of(Fixtures.ONE_FOUR, true),
                Arguments.of(Fixtures.TWO_FIVE, true),
                Arguments.of(Fixtures.TEN_SIX, true),
                Arguments.of(Fixtures.FIVE_FIVE, false)
        );
    }

    private static Stream<Arguments> provideIsSamePalaceData() {
        return Stream.of(
                Arguments.of(Fixtures.ONE_FOUR, Fixtures.TWO_FIVE, true),
                Arguments.of(Fixtures.TEN_FOUR, Fixtures.EIGHT_FIVE, true),
                Arguments.of(Fixtures.ONE_FOUR, Fixtures.EIGHT_FIVE, false),
                Arguments.of(Fixtures.FIVE_FIVE, Fixtures.SIX_FOUR, false)
        );
    }

    private static Stream<Arguments> provideCanMoveDiagonalData() {
        return Stream.of(
                Arguments.of(Fixtures.ONE_FOUR, Fixtures.TWO_FIVE, true),
                Arguments.of(Fixtures.THREE_FOUR, Fixtures.ONE_SIX, true),
                Arguments.of(Fixtures.NINE_FIVE, Fixtures.TEN_SIX, true),
                Arguments.of(Fixtures.NINE_FOUR, Fixtures.EIGHT_FIVE, false),
                Arguments.of(Fixtures.TEN_FOUR, Fixtures.SEVEN_SEVEN, false),
                Arguments.of(Fixtures.FIVE_FOUR, Fixtures.SIX_FIVE, false)
        );
    }
}