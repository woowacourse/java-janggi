package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Position;
import janggi.domain.side.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JolTest {

    @Test
    @DisplayName("초나라 졸은 앞으로 한 칸 이동할 수 있다.")
    void chuIsValidMovePatternForward() {
        // given
        Jol jol = new Jol(TeamType.CHU);

        // when & then
        assertThat(jol.isValidMovePattern(createPosition(4, 4), createPosition(4, 5))).isTrue();
    }

    @Test
    @DisplayName("한나라 졸은 앞으로 한 칸 이동할 수 있다.")
    void hanIsValidMovePatternForward() {
        // given
        Jol jol = new Jol(TeamType.HAN);

        // when & then
        assertThat(jol.isValidMovePattern(createPosition(4, 4), createPosition(4, 3))).isTrue();
    }

    @Test
    @DisplayName("졸은 좌우로 한 칸 이동할 수 있다.")
    void isValidMovePatternHorizontally() {
        // given
        Jol jolOfChu = new Jol(TeamType.CHU);
        Jol jolOfHan = new Jol(TeamType.HAN);

        // when & then
        assertAll(
            () -> assertThat(jolOfChu.isValidMovePattern(createPosition(4, 4), createPosition(5, 4))).isTrue(),
            () -> assertThat(jolOfChu.isValidMovePattern(createPosition(4, 4), createPosition(3, 4))).isTrue(),
            () -> assertThat(jolOfHan.isValidMovePattern(createPosition(4, 4), createPosition(5, 4))).isTrue(),
            () -> assertThat(jolOfHan.isValidMovePattern(createPosition(4, 4), createPosition(3, 4))).isTrue()
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
            () -> assertThat(jolOfChu.isValidMovePattern(createPosition(4, 4), createPosition(4, 3))).isFalse(),
            () -> assertThat(hanJol.isValidMovePattern(createPosition(4, 4), createPosition(4, 5))).isFalse()
        );
    }

    @Test
    @DisplayName("졸은 두 칸 이상 이동하거나 대각선으로 이동할 수 없다.")
    void cannotMoveInvalidPath() {
        // given
        Jol jol = new Jol(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(jol.isValidMovePattern(createPosition(4, 4), createPosition(4, 6))).isFalse(),
            () -> assertThat(jol.isValidMovePattern(createPosition(4, 4), createPosition(2, 4))).isFalse(),
            () -> assertThat(jol.isValidMovePattern(createPosition(4, 4), createPosition(5, 5))).isFalse(),
            () -> assertThat(jol.isValidMovePattern(createPosition(4, 4), createPosition(3, 3))).isFalse()
        );
    }

    @Test
    @DisplayName("졸은 제자리로 이동할 수 없다.")
    void cannotMoveSamePosition() {
        // given
        Jol jol = new Jol(TeamType.CHU);

        // when & then
        assertThat(jol.isValidMovePattern(createPosition(4, 4), createPosition(4, 4))).isFalse();
    }

    private Position createPosition(int x, int y) {
        return new Position(x, y);
    }
}
