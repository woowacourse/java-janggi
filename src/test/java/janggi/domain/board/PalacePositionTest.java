package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PalacePositionTest {

    @Test
    @DisplayName("궁성 중앙 위치에서는 대각선 4방향 이동이 모두 가능해야 한다")
    void provideAllDiagonalDirectionsAtCenter() {
        Position center = new Position(1, 4);
        EnumSet<Direction> directions = PalacePosition.palaceDirections(center);
        assertThat(directions).containsExactlyInAnyOrder(
                Direction.NE, Direction.NW, Direction.SE, Direction.SW
        );
    }

    @ParameterizedTest
    @CsvSource({"1, 3, true", "0, 0, false", "8, 4, true"})
    @DisplayName("주어진 좌표가 한나라 또는 초나라의 궁성 내부인지 정확히 판별해야 한다")
    void verifyPalacePosition(int row, int col, boolean expected) {
        Position position = new Position(row, col);
        boolean isPalace = PalacePosition.isPalacePosition(position);
        assertThat(isPalace).isEqualTo(expected);
    }

    @Test
    @DisplayName("궁성의 전체 좌표 개수는 9개여야 한다")
    void verifyTotalPalacePositionsCount() {
        Destinations palacePositions = PalacePosition.palacePositions();
        assertThat(palacePositions.getDestinations()).hasSize(9);
    }
}
