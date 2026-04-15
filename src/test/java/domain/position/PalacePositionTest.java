package domain.position;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PalacePositionTest {

    @Test
    void 궁_안_위치이면_참() {
        assertThat(PalacePosition.isPalacePosition(new Position(9, 5))).isTrue();
    }

    @Test
    void 궁_밖_위치이면_거짓() {
        assertThat(PalacePosition.isPalacePosition(new Position(5, 5))).isFalse();
    }

    @Test
    void 궁_코너는_대각선_이동_가능() {
        assertThat(PalacePosition.isCanMoveDiagonal(new Position(8, 4))).isTrue();
    }

    @Test
    void 궁_중앙은_대각선_이동_가능() {
        assertThat(PalacePosition.isCanMoveDiagonal(new Position(9, 5))).isTrue();
    }

    @Test
    void 궁_변은_대각선_이동_불가() {
        assertThat(PalacePosition.isCanMoveDiagonal(new Position(8, 5))).isFalse();
    }

    @Test
    void 궁_밖은_대각선_이동_불가() {
        assertThat(PalacePosition.isCanMoveDiagonal(new Position(5, 5))).isFalse();
    }
}
