package janggi.piece;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class PieceTest {

    @Test
    void 기물의_팀을_확인한다() {
        Piece piece = new Piece("초나라");

        assertThat(piece.checkConuntry("초나라")).isTrue();
    }
}
