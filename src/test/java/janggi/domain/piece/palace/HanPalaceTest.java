package janggi.domain.piece.palace;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Fixtures;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Point;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HanPalaceTest {

    @DisplayName("출발지와 목적지가 궁상 내부가 아니면 움직일 수 없다.")
    @Test
    void cannotMove_whenOutPalacePoints() {
        //given
        HanPalace hanPalace = new HanPalace();

        //when
        boolean result = hanPalace.canMoveInPalace(Fixtures.ONE_ONE, Fixtures.ONE_TWO, Direction.RIGHT);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("궁상 내부의 대각선을 이동할 수 있는 좌표가 아닌데 대각선으로 가려고 하면 움직일 수 없다.")
    @Test
    void cannotMove_whenCannotMoveDiagonal() {
        //given
        HanPalace hanPalace = new HanPalace();

        //when
        boolean result = hanPalace.canMoveInPalace(Fixtures.TWO_FOUR, Fixtures.ONE_FIVE, Direction.UP_RIGHT_DIAGONAL);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("궁상 내부의 대각선을 이동할 수 있는 좌표일때 대각선으로 가려고 하면 움직일 수 있다.")
    @Test
    void canMove_whenCanMoveDiagonal() {
        //given
        HanPalace hanPalace = new HanPalace();

        //when
        boolean result = hanPalace.canMoveInPalace(Fixtures.THREE_FOUR, Fixtures.TWO_FIVE, Direction.UP_RIGHT_DIAGONAL);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("궁성 내부에서 이동할 수 있는 목적지라면 이동할 수 있다.")
    @Test
    void canMoveInPalace() {
        //given
        HanPalace hanPalace = new HanPalace();

        //when
        boolean result = hanPalace.canMoveInPalace(Fixtures.TWO_FIVE, Fixtures.TWO_FOUR, Direction.LEFT);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("해당 좌표가 궁상인지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideIsInPalaceData")
    void isInPalace(Point point, boolean expected) {
        //given
        HanPalace hanPalace = new HanPalace();

        //when
        boolean result = hanPalace.isInPalace(point);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideIsInPalaceData() {
        return Stream.of(
                Arguments.of(Fixtures.ONE_FOUR, true),
                Arguments.of(Fixtures.TWO_FIVE, true),
                Arguments.of(Fixtures.FIVE_FIVE, false)
        );
    }
}