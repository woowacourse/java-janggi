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

class LeftWingTest {

    @Nested
    class 좌진_내부_기물의_초기_위치를_반환한다 {

        @Test
        void 한_진영의_초기_위치를_반환한다() {
            // given
            Side side = Side.HAN;
            LeftWing leftWing = new LeftWing(List.of(
                    new Horse(side),
                    new Elephant(side)
            ));

            Map<Intersection, Piece> expected = Map.of(
                    new Intersection(1, 8), new Horse(side),
                    new Intersection(1, 7), new Elephant(side)
            );

            // when
            Map<Intersection, Piece> actual = leftWing.setUpPieces(side);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 초_진영의_초기_위치를_반환한다() {
            // given
            Side side = Side.CHO;
            LeftWing leftWing = new LeftWing(List.of(
                    new Horse(side),
                    new Elephant(side)
            ));

            Map<Intersection, Piece> expected = Map.of(
                    new Intersection(10, 2), new Horse(side),
                    new Intersection(10, 3), new Elephant(side)
            );

            // when
            Map<Intersection, Piece> actual = leftWing.setUpPieces(side);

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }
}
