package domain.piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PieceTest {
    Piece choPiece;
    Piece hanPiece;

    @BeforeEach
    void setUp() {
        choPiece = new Piece(Camp.CHO, PieceType.CANNON, PieceType.CANNON.createStrategy(Camp.CHO));
        hanPiece = new Piece(Camp.HAN, PieceType.CANNON, PieceType.CANNON.createStrategy(Camp.HAN));
    }

    @Test
    @DisplayName("기물은 자신의 진영 정보를 알고 있다.")
    void determine_ChoOrHan() {
        Camp choCamp = choPiece.camp();
        Camp hanCamp = hanPiece.camp();

        assertThat(choCamp).isEqualTo(Camp.CHO);
        assertThat(hanCamp).isEqualTo(Camp.HAN);
    }

    @Test
    @DisplayName("기물은 자신의 타입 정보를 알고 있다.")
    void determine_Self_Type() {
        PieceType pieceType = choPiece.type();

        assertThat(pieceType).isEqualTo(PieceType.CANNON);
    }
}
