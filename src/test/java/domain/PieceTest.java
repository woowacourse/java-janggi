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
}
