package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Side;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RightWingTest {

    @Nested
    class 우진_내부_기물의_초기_위치를_반환한다 {

        @Test
        void 한_진영의_초기_위치를_반환한다() {
            // given
            Side side = Side.HAN;
            RightWing rightWing = new RightWing(List.of(
                    new Horse(side),
                    new Elephant(side)
            ));

            Map<Intersection, Piece> expected = Map.of(
                    new Intersection(1, 3), new Horse(side),
                    new Intersection(1, 2), new Elephant(side)
            );

            // when
            Map<Intersection, Piece> actual = rightWing.setUpPieces(side);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 초_진영의_초기_위치를_반환한다() {
            // given
            Side side = Side.CHO;
            RightWing rightWing = new RightWing(List.of(
                    new Horse(side),
                    new Elephant(side)
            ));

            Map<Intersection, Piece> expected = Map.of(
                    new Intersection(10, 7), new Horse(side),
                    new Intersection(10, 8), new Elephant(side)
            );

            // when
            Map<Intersection, Piece> actual = rightWing.setUpPieces(side);

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }
}
