package janggi.temp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GeneralTest {

    /*
     * 한의 궁성 (column, row)
     * (3, 0) (4, 0) (5, 0)
     * (3, 1) (4, 1) (5, 1)
     * (3, 2) (4, 2) (5, 2)
     *
     * 초의 궁성 (column, row)
     * (3, 7) (4, 7) (5, 7)
     * (3, 8) (4, 8) (5, 8)
     * (3, 9) (4, 9) (5, 9)
     */

    @DisplayName("한궁은 궁성의 가운데에서 모든 방향으로 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void testMoveHanGeneral(Position destination) {
        // given
        General generalInCenter = new General(new Position(Column.FOUR, Row.ONE), Team.HAN);
        // when
        General movedGeneral = generalInCenter.move(destination);
        // then
        assertThat(movedGeneral).isEqualTo(new General(destination, Team.HAN));
    }

    @DisplayName("초궁은 궁성의 가운데에서 모든 방향으로 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void testMoveChoGeneral(Position destination) {
        // given
        General generalInCenter = new General(new Position(Column.FOUR, Row.EIGHT), Team.CHO);
        // when
        General movedGeneral = generalInCenter.move(destination);
        // then
        assertThat(movedGeneral).isEqualTo(new General(destination, Team.CHO));
    }

    @DisplayName("궁은 한 칸만 이동할 수 있다.")
    @Test
    void testMovingRuleValidation() {
        // given
        General general = new General(new Position(Column.THREE, Row.ZERO), Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> general.move(new Position(Column.FIVE, Row.TWO)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 궁의 규칙에 어긋나는 움직입입니다.");
    }

    @DisplayName("궁은 궁성 밖으로 이동할 수 없다.")
    @Test
    void testValidatePalaceDestination() {
        // given
        General general = new General(new Position(Column.FOUR, Row.TWO), Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> general.move(new Position(Column.FOUR, Row.THREE)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 궁은 궁성 밖으로 이동할 수 없습니다.");
    }

    @DisplayName("궁은 궁성의 가운데가 아니고, 모서리가 아닌 위치에서는 대각선으로 이동할 수 없다.")
    @Test
    void testInvalidMoveInPalaceSides() {
        // given
        Position currentPosition = new Position(Column.FOUR, Row.TWO);
        Position destination = new Position(Column.THREE, Row.ONE);
        General general = new General(currentPosition, Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> general.move(destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 궁의 규칙에 어긋나는 움직입입니다.");
    }

    @DisplayName("궁은 자기 위치로 이동할 수 없다.")
    @Test
    void test4() {
        // given
        Position currentPosition = new Position(Column.FOUR, Row.TWO);
        General general = new General(currentPosition, Team.HAN);
        // when
        // then
        assertThatThrownBy(() -> general.move(currentPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 본인의 위치로는 이동할 수 없습니다.");
    }

    // TODO 같은 팀이 있는 위치로 이동할 수 없다
    // TODO 상대 팀이 있으면 잡으면서 이동한다

    private static Stream<Arguments> testMoveHanGeneral() {
        return Stream.of(
                Arguments.of(new Position(Column.THREE, Row.ZERO)),
                Arguments.of(new Position(Column.FOUR, Row.ZERO)),
                Arguments.of(new Position(Column.FIVE, Row.ZERO)),
                Arguments.of(new Position(Column.THREE, Row.ONE)),
                Arguments.of(new Position(Column.FIVE, Row.ONE)),
                Arguments.of(new Position(Column.THREE, Row.TWO)),
                Arguments.of(new Position(Column.FOUR, Row.TWO)),
                Arguments.of(new Position(Column.FIVE, Row.TWO))
        );
    }

    private static Stream<Arguments> testMoveChoGeneral() {
        return Stream.of(
                Arguments.of(new Position(Column.THREE, Row.SEVEN)),
                Arguments.of(new Position(Column.FOUR, Row.SEVEN)),
                Arguments.of(new Position(Column.FIVE, Row.SEVEN)),
                Arguments.of(new Position(Column.THREE, Row.EIGHT)),
                Arguments.of(new Position(Column.FIVE, Row.EIGHT)),
                Arguments.of(new Position(Column.THREE, Row.NINE)),
                Arguments.of(new Position(Column.FOUR, Row.NINE)),
                Arguments.of(new Position(Column.FIVE, Row.NINE))
        );
    }
}
