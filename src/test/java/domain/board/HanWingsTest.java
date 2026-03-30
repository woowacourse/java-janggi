package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Side;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class HanWingsTest {

    @Test
    void 한의_좌진과_우진_기물의_초기_위치를_반환한다() {
        // given
        Piece first = new Horse(Side.HAN);
        Piece second = new Elephant(Side.HAN);
        Piece third = new Horse(Side.HAN);
        Piece fourth = new Elephant(Side.HAN);

        HanWings hanWings = new HanWings(
                List.of(first, second),
                List.of(third, fourth)
        );

        // when
        Map<Intersection, Piece> setUpPieces = hanWings.setUpPieces();

        // then
        assertThat(setUpPieces.get(new Intersection(1, 8))).isEqualTo(first);
        assertThat(setUpPieces.get(new Intersection(1, 7))).isEqualTo(second);
        assertThat(setUpPieces.get(new Intersection(1, 3))).isEqualTo(third);
        assertThat(setUpPieces.get(new Intersection(1, 2))).isEqualTo(fourth);
    }
}
