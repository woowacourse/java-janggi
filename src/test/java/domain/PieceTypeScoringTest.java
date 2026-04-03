package domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTypeScoringTest {

    @Test
    void 차는_장기력_13이다() {
        assertThat(PieceType.ROOK.materialPoints()).isEqualTo(MaterialPoints.of(13));
    }

    @Test
    void 포는_장기력_7이다() {
        assertThat(PieceType.CANNON.materialPoints()).isEqualTo(MaterialPoints.of(7));
    }

    @Test
    void 마는_장기력_5이다() {
        assertThat(PieceType.HORSE.materialPoints()).isEqualTo(MaterialPoints.of(5));
    }

    @Test
    void 상은_장기력_3이다() {
        assertThat(PieceType.ELEPHANT.materialPoints()).isEqualTo(MaterialPoints.of(3));
    }

    @Test
    void 사는_장기력_3이다() {
        assertThat(PieceType.GUARD.materialPoints()).isEqualTo(MaterialPoints.of(3));
    }

    @Test
    void 졸은_장기력_2이다() {
        assertThat(PieceType.PAWN.materialPoints()).isEqualTo(MaterialPoints.of(2));
    }

    @Test
    void 왕은_장기력_0이다() {
        assertThat(PieceType.KING.materialPoints()).isEqualTo(MaterialPoints.zero());
    }
}
