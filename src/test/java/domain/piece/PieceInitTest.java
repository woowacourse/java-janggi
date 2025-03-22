package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.General;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.Soldier;
import java.util.List;
import org.junit.jupiter.api.Test;

class PieceInitTest {

    @Test
    void 한_영역의_기물_리스트를_반환한다() {
        // when
        List<Piece> pieces = PieceInit.initHanPieces();

        // then
        assertAll(() -> {
            assertThat(pieces.stream().filter(p -> p instanceof General).count()).isEqualTo(1);
            assertThat(pieces.stream().filter(p -> p instanceof Chariot).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Cannon).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Horse).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Elephant).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Guard).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Soldier).count()).isEqualTo(5);
        });
    }

    @Test
    void 초_영역의_기물_리스트를_반환한다() {
        // when
        List<Piece> pieces = PieceInit.initChoPieces();

        // then
        assertAll(() -> {
            assertThat(pieces.stream().filter(p -> p instanceof General).count()).isEqualTo(1);
            assertThat(pieces.stream().filter(p -> p instanceof Chariot).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Cannon).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Horse).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Elephant).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Guard).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Soldier).count()).isEqualTo(5);
        });
    }
}
