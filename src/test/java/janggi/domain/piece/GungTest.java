package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.side.TeamType;
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
            () -> assertThat(gung.isValidMovePattern(4, 4, 4, 5)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(4, 4, 4, 3)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(4, 4, 5, 4)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(4, 4, 3, 4)).isTrue()
        );
    }

    @Test
    @DisplayName("궁은 대각선 한 칸 이동할 수 있다.")
    void isValidMovePatternDiagonalOneStep() {
        // given
        Gung gung = new Gung(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(gung.isValidMovePattern(4, 4, 5, 5)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(4, 4, 5, 3)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(4, 4, 3, 5)).isTrue(),
            () -> assertThat(gung.isValidMovePattern(4, 4, 3, 3)).isTrue()
        );
    }

    @Test
    @DisplayName("궁은 두 칸 이상 이동할 수 없다.")
    void cannotMoveOverOneStep() {
        // given
        Gung gung = new Gung(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(gung.isValidMovePattern(4, 4, 6, 4)).isFalse(),
            () -> assertThat(gung.isValidMovePattern(4, 4, 6, 6)).isFalse(),
            () -> assertThat(gung.isValidMovePattern(4, 4, 4, 6)).isFalse(),
            () -> assertThat(gung.isValidMovePattern(4, 4, 2, 4)).isFalse()
        );
    }

    @Test
    @DisplayName("궁은 제자리로 이동할 수 없다.")
    void cannotMoveSamePosition() {
        // given
        Gung gung = new Gung(TeamType.CHU);

        // when & then
        assertThat(gung.isValidMovePattern(4, 4, 4, 4)).isFalse();
    }
}
