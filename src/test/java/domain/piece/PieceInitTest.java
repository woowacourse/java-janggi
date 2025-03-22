package domain.piece;

import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.King;
import domain.piece.category.Soldier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PieceInitTest {

    @Test
    void 한_영역의_기물_리스트를_반환한다() {
        // when
        List<Piece> pieces = PieceInit.initHanPieces();

        // then
        assertAll(() -> {
            assertThat(pieces.stream().filter(p -> p instanceof King).count()).isEqualTo(1);
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
            assertThat(pieces.stream().filter(p -> p instanceof King).count()).isEqualTo(1);
            assertThat(pieces.stream().filter(p -> p instanceof Chariot).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Cannon).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Horse).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Elephant).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Guard).count()).isEqualTo(2);
            assertThat(pieces.stream().filter(p -> p instanceof Soldier).count()).isEqualTo(5);
        });
    }
}
