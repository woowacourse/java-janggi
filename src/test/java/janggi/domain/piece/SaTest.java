package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Position;
import janggi.domain.side.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SaTest {

    @Test
    @DisplayName("사는 상하좌우 한 칸 이동할 수 있다.")
    void isValidMovePatternStraightOneStep() {
        // given
        Sa sa = new Sa(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(4, 5))).isTrue(),
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(4, 3))).isTrue(),
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(5, 4))).isTrue(),
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(3, 4))).isTrue()
        );
    }

    @Test
    @DisplayName("사는 대각선 한 칸 이동할 수 있다.")
    void isValidMovePatternDiagonalOneStep() {
        // given
        Sa sa = new Sa(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(5, 5))).isTrue(),
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(5, 3))).isTrue(),
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(3, 5))).isTrue(),
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(3, 3))).isTrue()
        );
    }

    @Test
    @DisplayName("사는 두 칸 이상 이동하거나 제자리로 이동할 수 없다.")
    void cannotMoveInvalidPattern() {
        // given
        Sa sa = new Sa(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(6, 4))).isFalse(),
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(6, 6))).isFalse(),
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(4, 6))).isFalse(),
            () -> assertThat(sa.isValidMovePattern(createPosition(4, 4), createPosition(4, 4))).isFalse()
        );
    }

    private Position createPosition(int x, int y) {
        return new Position(x, y);
    }
}
