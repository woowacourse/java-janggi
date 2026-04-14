package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.team.TeamType;
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
            () -> assertThat(sa.isValidMovePattern(5, 2, 5, 3)).isTrue(),
            () -> assertThat(sa.isValidMovePattern(5, 2, 5, 1)).isTrue(),
            () -> assertThat(sa.isValidMovePattern(5, 2, 6, 2)).isTrue(),
            () -> assertThat(sa.isValidMovePattern(5, 2, 4, 2)).isTrue()
        );
    }

    @Test
    @DisplayName("사는 대각선 한 칸 이동할 수 있다.")
    void isValidMovePatternDiagonalOneStep() {
        // given
        Sa sa = new Sa(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(sa.isValidMovePattern(5, 2, 6, 3)).isTrue(),
            () -> assertThat(sa.isValidMovePattern(5, 2, 6, 1)).isTrue(),
            () -> assertThat(sa.isValidMovePattern(5, 2, 4, 3)).isTrue(),
            () -> assertThat(sa.isValidMovePattern(5, 2, 4, 1)).isTrue()
        );
    }

    @Test
    @DisplayName("사는 궁성 밖으로 나가거나 연결되지 않은 대각선으로 이동할 수 없다.")
    void cannotMoveInvalidPattern() {
        // given
        Sa sa = new Sa(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(sa.isValidMovePattern(5, 2, 5, 4)).isFalse(),
            () -> assertThat(sa.isValidMovePattern(4, 1, 6, 3)).isFalse(),
            () -> assertThat(sa.isValidMovePattern(4, 2, 5, 1)).isFalse(),
            () -> assertThat(sa.isValidMovePattern(5, 2, 5, 2)).isFalse()
        );
    }
}
