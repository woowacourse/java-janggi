package domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @Test
    void of는_팀과_종류를_담은_기물을_반환한다() {
        Piece piece = Piece.of(TeamColor.CHO, PieceType.KING);

        assertThat(piece.getTeamColor()).isEqualTo(TeamColor.CHO);
        assertThat(piece.getPieceType()).isEqualTo(PieceType.KING);
    }

    @Test
    void materialPoints는_기물_종류의_장기력을_반환한다() {
        Piece piece = Piece.of(TeamColor.HAN, PieceType.ROOK);

        assertThat(piece.materialPoints()).isEqualTo(MaterialPoints.of(13));
    }

    @Test
    void isKing은_왕인지_확인한다() {
        Piece piece = Piece.of(TeamColor.CHO, PieceType.KING);
        assertThat(piece.isKing()).isTrue();
    }

    @Test
    void isKing은_왕이_아니면_거짓이다() {
        Piece piece = Piece.of(TeamColor.HAN, PieceType.ROOK);
        assertThat(piece.isKing()).isFalse();
    }
}
