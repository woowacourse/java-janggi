package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.team.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GungTest {

    @Test
    @DisplayName("궁은 상하좌우 한 칸 이동할 수 있다.")
    void isValidMovePatternStraightOneStep() {
        // given
        Gung gung = new Gung(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(gung.isValidMovePattern(5, 2, 5, 3)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(5, 2, 5, 1)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(5, 2, 6, 2)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(5, 2, 4, 2)).isTrue()
        );
    }

    @Test
    @DisplayName("궁은 대각선 한 칸 이동할 수 있다.")
    void isValidMovePatternDiagonalOneStep() {
        // given
        Gung gung = new Gung(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(gung.isValidMovePattern(5, 2, 6, 3)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(5, 2, 6, 1)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(5, 2, 4, 3)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(5, 2, 4, 1)).isTrue()
        );
    }

    @Test
    @DisplayName("궁은 궁성 밖으로 나가거나 연결되지 않은 대각선으로 이동할 수 없다.")
    void cannotMoveOverOneStep() {
        // given
        Gung gung = new Gung(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(gung.isValidMovePattern(5, 2, 5, 4)).isFalse(),
            () -> assertThat(gung.isValidMovePattern(4, 1, 6, 3)).isFalse(),
            () -> assertThat(gung.isValidMovePattern(4, 2, 5, 1)).isFalse(),
            () -> assertThat(gung.isValidMovePattern(5, 2, 3, 2)).isFalse()
        );
    }

    @Test
    @DisplayName("궁은 제자리로 이동할 수 없다.")
    void cannotMoveSamePosition() {
        // given
        Gung gung = new Gung(TeamType.CHU);

        // when & then
        assertThat(gung.isValidMovePattern(5, 2, 5, 2)).isFalse();
    }
}
