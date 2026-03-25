package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GungTest {

    @Test
    @DisplayName("시작점에서 상하좌우 한 칸 이동할 수 있다.")
    void canMoveStraightOneStep() {
        // given
        Gung gung = new Gung();

        // when & then
        assertAll(
            () -> assertThat(gung.canMove(4, 4, 4, 5)).isTrue(),
            () -> assertThat(gung.canMove(4, 4, 4, 3)).isTrue(),
            () -> assertThat(gung.canMove(4, 4, 5, 4)).isTrue(),
            () -> assertThat(gung.canMove(4, 4, 3, 4)).isTrue()
        );
    }

    @Test
    @DisplayName("시작점에서 대각선 한 칸 이동할 수 있다.")
    void canMoveDiagonalOneStep() {
        // given
        Gung gung = new Gung();

        // when & then
        assertAll(
            () -> assertThat(gung.canMove(4, 4, 5, 5)).isTrue(),
            () -> assertThat(gung.canMove(4, 4, 5, 3)).isTrue(),
            () -> assertThat(gung.canMove(4, 4, 3, 5)).isTrue(),
            () -> assertThat(gung.canMove(4, 4, 3, 3)).isTrue()
        );
    }

    @Test
    @DisplayName("시작점에서 두 칸 이상 이동할 수 없다.")
    void cannotMoveOverOneStep() {
        // given
        Gung gung = new Gung();

        // when & then
        assertAll(
            () -> assertThat(gung.canMove(4, 4, 6, 4)).isFalse(),
            () -> assertThat(gung.canMove(4, 4, 6, 6)).isFalse(),
            () -> assertThat(gung.canMove(4, 4, 4, 6)).isFalse()
        );

    }

    @Test
    @DisplayName("제자리로는 이동할 수 없다.")
    void cannotMoveSamePosition() {
        // given
        Gung gung = new Gung();

        // when & then
        assertThat(gung.canMove(4, 4, 4, 4)).isFalse();
    }
}
