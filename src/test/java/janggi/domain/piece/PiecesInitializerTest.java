package janggi.domain.piece;

import static janggi.domain.BoardSetup.INNER_ELEPHANT_SETUP;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesInitializerTest {

    @DisplayName("장기말을 초기화한다.")
    @Test
    void initializeTest() {

        // given
        final List<Piece> pieces = PiecesInitializer.initializePieces(INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP);

        // when & then
        assertThat(pieces.size()).isEqualTo(32);
    }
}
