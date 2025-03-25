package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.rule.MoveVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @DisplayName("두 개의 position의 차이를 계산한다.")
    @Test
    void testGetDiff() {
        // given
        final Position start = new Position(Row.SIX, Column.SIX);
        final Position end = new Position(Row.FIVE, Column.SEVEN);
        // when
        final MoveVector actual = end.calculateVectorDiff(start);
        // then
        assertThat(actual).isEqualTo(new MoveVector(-1, 1));
    }

    @DisplayName("궁성인지 판단한다.")
    @Test
    void testIsPalace() {
        // given
        final Position position1 = new Position(Row.THREE, Column.THREE);
        final Position position2 = new Position(Row.ONE, Column.FIVE);
        // when
        // then
        assertAll(
                () -> assertThat(position1.isPalace()).isFalse(),
                () -> assertThat(position2.isPalace()).isTrue()
        );
    }

    @DisplayName("궁성 중앙인지 판단한다.")
    @Test
    void testIsCenterOfPalace() {
        // given
        final Position position1 = new Position(Row.EIGHT, Column.FOUR);
        final Position position2 = new Position(Row.ONE, Column.FOUR);
        // when
        // then
        assertAll(
                () -> assertThat(position1.isCenterOfPalace()).isTrue(),
                () -> assertThat(position2.isCenterOfPalace()).isTrue()
        );
    }
}
