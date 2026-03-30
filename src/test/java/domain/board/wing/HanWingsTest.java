package domain.board.wing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HanWingsTest {

    @Nested
    class 생성할_때_기물의_진영을_검증한다 {

        private final List<Piece> illegalSideWing = List.of(
                new Piece(PieceType.HORSE, Side.HAN),
                new Piece(PieceType.ELEPHANT, Side.CHO)
        );
        private final List<Piece> legalSideWing = List.of(
                new Piece(PieceType.HORSE, Side.HAN),
                new Piece(PieceType.ELEPHANT, Side.HAN)
        );

        @Test
        void 좌진에_한이_아닌_기물이_있다면_예외를_던진다() {
            assertThatThrownBy(() -> new HanWings(illegalSideWing, legalSideWing))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 우진에_한이_아닌_기물이_있다면_예외를_던진다() {
            assertThatThrownBy(() -> new HanWings(legalSideWing, illegalSideWing))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 좌진과_우진에_한_기물만_있다면_정상적으로_생성된다() {
            assertThatNoException().isThrownBy(() -> new HanWings(legalSideWing, legalSideWing));
        }
    }

    @Test
    void 한의_좌진과_우진_기물의_초기_위치를_반환한다() {
        // given
        Piece first = new Piece(PieceType.HORSE, Side.HAN);
        Piece second = new Piece(PieceType.ELEPHANT, Side.HAN);
        Piece third = new Piece(PieceType.HORSE, Side.HAN);
        Piece fourth = new Piece(PieceType.ELEPHANT, Side.HAN);

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
