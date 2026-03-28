package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.game.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PieceTest {

    @DisplayName("기물 생성 시 진영, 타입, 번호 중 하나라도 null이면 예외가 발생한다")
    @Test
    void create_NullArguments_ThrowsException() {
        assertThatThrownBy(() -> new Piece(null, PieceType.CHO_SOLDIER, "0"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Piece(Side.CHO, null, "0"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Piece(Side.CHO, PieceType.CHO_SOLDIER, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("기물 생성 시 번호가 공백이거나 비어있으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void create_BlankNumber_ThrowsException(String blankNumber) {
        assertThatThrownBy(() -> new Piece(Side.CHO, PieceType.CHO_SOLDIER, blankNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비교 대상 기물이 같은 진영이면 참을 반환한다")
    @Test
    void isSameSide_SameSidePiece_ReturnsTrue() {
        Piece choPiece1 = new Piece(Side.CHO, PieceType.CHO_SOLDIER, "0");
        Piece choPiece2 = new Piece(Side.CHO, PieceType.CHARIOT, "1");

        assertThat(choPiece1.isSameSide(choPiece2)).isTrue();
    }

    @DisplayName("비교 대상 기물이 다른 진영이거나 null이면 거짓을 반환한다")
    @Test
    void isSameSide_DifferentSideOrNull_ReturnsFalse() {
        Piece choPiece = new Piece(Side.CHO, PieceType.CHO_SOLDIER, "0");
        Piece hanPiece = new Piece(Side.HAN, PieceType.HAN_SOLDIER, "0");

        assertThat(choPiece.isSameSide(hanPiece)).isFalse();
        assertThat(choPiece.isSameSide(null)).isFalse();
    }

    @DisplayName("기물의 타입이 포(CANNON)인지 확인한다")
    @Test
    void isCannon_CannonType_ReturnsTrue() {
        Piece cannon = new Piece(Side.CHO, PieceType.CANNON, "0");
        Piece soldier = new Piece(Side.CHO, PieceType.CHO_SOLDIER, "0");

        assertThat(cannon.isCannon()).isTrue();
        assertThat(soldier.isCannon()).isFalse();
    }

    @DisplayName("기물의 타입이 궁(PALACE)인지 확인한다")
    @Test
    void isPalace_PalaceType_ReturnsTrue() {
        Piece palace = new Piece(Side.CHO, PieceType.PALACE, "0");
        Piece soldier = new Piece(Side.CHO, PieceType.CHO_SOLDIER, "0");

        assertThat(palace.isPalace()).isTrue();
        assertThat(soldier.isPalace()).isFalse();
    }

    @DisplayName("기물이 특정 진영에 속해 있는지 확인한다")
    @Test
    void isBelongTo_MatchesSide_ReturnsTrue() {
        Piece piece = new Piece(Side.CHO, PieceType.CHO_SOLDIER, "0");

        assertThat(piece.isBelongTo(Side.CHO)).isTrue();
        assertThat(piece.isBelongTo(Side.HAN)).isFalse();
    }
}
