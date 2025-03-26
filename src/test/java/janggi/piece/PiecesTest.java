package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesTest {

    @Test
    @DisplayName("각기 다른 위치의 32개의 장기 말이 생성된다.")
    void should_return_32_pieces_when_create_initial_pieces() {
        // when
        Pieces pieces = Pieces.init();

        // then
        assertThat(pieces.getPieces()).hasSize(32);
    }
}
