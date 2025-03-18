package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;

class PieceInitTest {

    @Test
    void 한_영역의_기물_리스트를_반환한다() {
        // when
        List<Piece> pieces = PieceInit.initHanPieces();

        // then
        assertAll(() -> {
            assertThat(pieces.stream().filter(p -> p instanceof King).count()).isEqualTo(1);
            assertThat(pieces.stream().filter(p -> p instanceof Rook).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Cannon).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Horse).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Elephant).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Advisor).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Pawn).count()).isEqualTo(5);
        });
    }

    @Test
    void 초_영역의_기물_리스트를_반환한다() {
        // when
        List<Piece> pieces = PieceInit.initChoPieces();

        // then
        assertAll(() -> {
            assertThat(pieces.stream().filter(p -> p instanceof King).count()).isEqualTo(1);
            assertThat(pieces.stream().filter(p -> p instanceof Rook).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Cannon).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Horse).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Elephant).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Advisor).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Pawn).count()).isEqualTo(5);
        });
    }
}
