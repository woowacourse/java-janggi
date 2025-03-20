package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesInitializerTest {

    @DisplayName("장기말을 초기화한다.")
    @Test
    void initializeTest() {

        // given
        List<Piece> pieces = PiecesInitializer.initializePieces();

        // when & then
        assertThat(pieces.size()).isEqualTo(32);
    }
}
