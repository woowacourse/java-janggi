package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {
    @DisplayName("기물 객체를 생성한다.")
    @Test
    void 기물_객체_생성_테스트() {
        // given
        Side side = Side.CHO;
        PieceType pieceType = PieceType.CANNON;
        String pieceNumber = "1";

        // when
        Piece piece = new Piece(side, pieceType, pieceNumber);

        // then
        assertThat(piece.getSide()).isEqualTo(side);
        assertThat(piece.getPieceType()).isEqualTo(pieceType);
        assertThat(piece.getPieceNumber()).isEqualTo(pieceNumber);
    }

    @DisplayName("같은 진영인지 확인한다.")
    @Test
    void 진영_확인_테스트() {
        // given
        Piece choPiece = new Piece(Side.CHO, PieceType.CANNON, "1");
        Piece anotherChoPiece = new Piece(Side.CHO, PieceType.HORSE, "1");
        Piece hanPiece = new Piece(Side.HAN, PieceType.CANNON, "1");

        // when & then
        assertThat(choPiece.isSameSide(anotherChoPiece)).isTrue();
        assertThat(choPiece.isSameSide(hanPiece)).isFalse();
    }

    @DisplayName("포(Cannon)인지 확인한다.")
    @Test
    void 포_확인_테스트() {
        // given
        Piece cannon = new Piece(Side.CHO, PieceType.CANNON, "1");
        Piece chariot = new Piece(Side.CHO, PieceType.CHARIOT, "1");

        // when & then
        assertThat(cannon.isCannon()).isTrue();
        assertThat(chariot.isCannon()).isFalse();
    }

    @DisplayName("같은 진영, 같은 타입, 같은 번호의 기물인 경우, 같은 객체로 취급한다.")
    @Test
    void 같은_기물_확인_테스트(){
        // given
        Piece cannon = new Piece(Side.CHO, PieceType.CANNON, "1");
        Piece cannon2 = new Piece(Side.CHO, PieceType.CANNON, "1");

        // when & then
        assertThat(cannon).isEqualTo(cannon2);
    }
}
