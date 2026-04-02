package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Side;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ChoWingsTest {

    @Test
    void 초의_좌진과_우진_기물의_초기_위치를_반환한다() {
        // given
        Piece first = new Horse(Side.CHO);
        Piece second = new Elephant(Side.CHO);
        Piece third = new Horse(Side.CHO);
        Piece fourth = new Elephant(Side.CHO);

        ChoWings choWings = new ChoWings(
                List.of(first, second),
                List.of(third, fourth)
        );

        // when
        Map<Intersection, Piece> setUpPieces = choWings.setUpPieces();

        // then
        assertThat(setUpPieces.get(new Intersection(10, 2))).isEqualTo(first);
        assertThat(setUpPieces.get(new Intersection(10, 3))).isEqualTo(second);
        assertThat(setUpPieces.get(new Intersection(10, 7))).isEqualTo(third);
        assertThat(setUpPieces.get(new Intersection(10, 8))).isEqualTo(fourth);
    }
}
