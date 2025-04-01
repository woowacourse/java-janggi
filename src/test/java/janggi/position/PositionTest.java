package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.moveStrategy.rule.MoveVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @DisplayName("두 개의 position의 차이를 계산한다.")
    @ParameterizedTest
    @CsvSource({
            "6, 6, 5, 7, -1, 1",  // start: (6,6), end: (5,7)
            "3, 2, 1, 1, -2, -1",  // start: (3,2), end: (1,1)
            "5, 5, 5, 5, 0, 0",    // start: (5,5), end: (5,5)
            "7, 4, 5, 6, -2, 2"    // start: (7,4), end: (5,6)
    })
    void testGetDiff(int startRow, int startCol, int endRow, int endCol, int expectedRowDiff, int expectedColDiff) {
        // given
        final Position start = new Position(Row.of(startRow), Column.of(startCol));
        final Position end = new Position(Row.of(endRow), Column.of(endCol));

        // when
        final MoveVector actual = end.calculateVectorDiff(start);

        // then
        assertThat(actual).isEqualTo(new MoveVector(expectedRowDiff, expectedColDiff));
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
