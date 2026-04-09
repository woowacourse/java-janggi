package domain.score;

import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceScorePolicyTest {

    private final PieceScorePolicy pieceScorePolicy = new PieceScorePolicy();

    @Test
    void 기물_타입에_따른_기본_점수를_반환한다() {
        assertThat(pieceScorePolicy.scoreOf(PieceType.KING)).isEqualTo(0.0);
        assertThat(pieceScorePolicy.scoreOf(PieceType.GUARD)).isEqualTo(3.0);
        assertThat(pieceScorePolicy.scoreOf(PieceType.ELEPHANT)).isEqualTo(3.0);
        assertThat(pieceScorePolicy.scoreOf(PieceType.HORSE)).isEqualTo(5.0);
        assertThat(pieceScorePolicy.scoreOf(PieceType.CANNON)).isEqualTo(7.0);
        assertThat(pieceScorePolicy.scoreOf(PieceType.ROOK)).isEqualTo(13.0);
        assertThat(pieceScorePolicy.scoreOf(PieceType.PAWN)).isEqualTo(2.0);
    }
}
