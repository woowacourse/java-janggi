package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.team.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JolTest {

    @Test
    @DisplayName("초나라 졸은 앞으로 한 칸 이동할 수 있다.")
    void chuIsValidMovePatternForward() {
        // given
        Jol jol = new Jol(TeamType.CHU);

        // when & then
        assertThat(jol.isValidMovePattern(4, 4, 4, 5)).isTrue();
    }

    @Test
    @DisplayName("한나라 졸은 앞으로 한 칸 이동할 수 있다.")
    void hanIsValidMovePatternForward() {
        // given
        Jol jol = new Jol(TeamType.HAN);

        // when & then
        assertThat(jol.isValidMovePattern(4, 4, 4, 3)).isTrue();
    }

    @Test
    @DisplayName("졸은 좌우로 한 칸 이동할 수 있다.")
    void isValidMovePatternHorizontally() {
        // given
        Jol jolOfChu = new Jol(TeamType.CHU);
        Jol jolOfHan = new Jol(TeamType.HAN);

        // when & then
        assertAll(
            () -> assertThat(jolOfChu.isValidMovePattern(4, 4, 5, 4)).isTrue(),
            () -> assertThat(jolOfChu.isValidMovePattern(4, 4, 3, 4)).isTrue(),
            () -> assertThat(jolOfHan.isValidMovePattern(4, 4, 5, 4)).isTrue(),
            () -> assertThat(jolOfHan.isValidMovePattern(4, 4, 3, 4)).isTrue()
        );
    }

    @Test
    @DisplayName("졸은 뒤로 이동할 수 없다.")
    void cannotMoveBackward() {
        // given
        Jol jolOfChu = new Jol(TeamType.CHU);
        Jol hanJol = new Jol(TeamType.HAN);

        // when & then
        assertAll(
            () -> assertThat(jolOfChu.isValidMovePattern(4, 4, 4, 3)).isFalse(),
            () -> assertThat(hanJol.isValidMovePattern(4, 4, 4, 5)).isFalse()
        );
    }

    @Test
    @DisplayName("졸은 궁성 안에서 전진 대각선으로 한 칸 이동할 수 있다.")
    void canMoveForwardDiagonallyInsidePalace() {
        // given
        Jol chuJol = new Jol(TeamType.CHU);
        Jol hanJol = new Jol(TeamType.HAN);

        // when & then
        assertAll(
            () -> assertThat(chuJol.isValidMovePattern(4, 1, 5, 2)).isTrue(),
            () -> assertThat(chuJol.isValidMovePattern(5, 2, 6, 3)).isTrue(),
            () -> assertThat(hanJol.isValidMovePattern(6, 10, 5, 9)).isTrue(),
            () -> assertThat(hanJol.isValidMovePattern(5, 9, 4, 8)).isTrue()
        );
    }

    @Test
    @DisplayName("졸은 두 칸 이상 이동하거나 대각선으로 이동할 수 없다.")
    void cannotMoveInvalidPath() {
        // given
        Jol jol = new Jol(TeamType.CHU);
        Jol hanJol = new Jol(TeamType.HAN);

        // when & then
        assertAll(
            () -> assertThat(jol.isValidMovePattern(4, 4, 4, 6)).isFalse(),
            () -> assertThat(jol.isValidMovePattern(4, 4, 2, 4)).isFalse(),
            () -> assertThat(jol.isValidMovePattern(4, 4, 5, 5)).isFalse(),
            () -> assertThat(jol.isValidMovePattern(4, 4, 3, 3)).isFalse(),
            () -> assertThat(jol.isValidMovePattern(5, 2, 4, 1)).isFalse(),
            () -> assertThat(hanJol.isValidMovePattern(5, 9, 6, 10)).isFalse(),
            () -> assertThat(jol.isValidMovePattern(4, 2, 5, 1)).isFalse()
        );
    }

    @Test
    @DisplayName("졸은 제자리로 이동할 수 없다.")
    void cannotMoveSamePosition() {
        // given
        Jol jol = new Jol(TeamType.CHU);

        // when & then
        assertThat(jol.isValidMovePattern(4, 4, 4, 4)).isFalse();
    }
}
