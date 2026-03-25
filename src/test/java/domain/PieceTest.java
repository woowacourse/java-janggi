package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PieceTest {
    Piece piece;

    @BeforeEach
    void setUp() {
        piece = new Piece(Camp.CHO, PieceType.CANNON);
    }

    @Test
    @DisplayName("기물은 자신의 진영 정보를 알고 있다.")
    void determine_ChoOrHan() {
        Camp camp = piece.camp();

        assertThat(camp).isEqualTo(Camp.CHO);
    }

    @Test
    @DisplayName("기물은 자신의 타입 정보를 알고 있다.")
    void determine_Self_Type() {
        PieceType pieceType = piece.type();

        assertThat(pieceType).isEqualTo(PieceType.CANNON);
    }
}
