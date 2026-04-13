package domain.board.wing;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
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
            RightWing rightWing = new RightWing(new WingPieces(
                    Piece.of(PieceType.HORSE, side),
                    Piece.of(PieceType.ELEPHANT, side)
            ));

            Map<Intersection, Piece> expected = Map.of(
                    new Intersection(1, 3), Piece.of(PieceType.HORSE, side),
                    new Intersection(1, 2), Piece.of(PieceType.ELEPHANT, side)
            );

            // when
            Map<Intersection, Piece> actual = rightWing.setUpPieces(side);

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 초_진영의_초기_위치를_반환한다() {
            // given
            Side side = Side.CHO;
            RightWing rightWing = new RightWing(new WingPieces(
                    Piece.of(PieceType.HORSE, side),
                    Piece.of(PieceType.ELEPHANT, side)
            ));

            Map<Intersection, Piece> expected = Map.of(
                    new Intersection(10, 7), Piece.of(PieceType.HORSE, side),
                    new Intersection(10, 8), Piece.of(PieceType.ELEPHANT, side)
            );

            // when
            Map<Intersection, Piece> actual = rightWing.setUpPieces(side);

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }
}
