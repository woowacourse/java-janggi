package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AlivePiecesTest {

    private static final Side SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;

    private Intersection emptyIntersection;
    private Intersection notEmptyIntersection;
    private Intersection sameSideIntersection;
    private Intersection oppositeSideIntersection;
    private AlivePieces alivePieces;

    @BeforeEach
    void setUpPieces() {
        emptyIntersection = new Intersection(3, 3);
        notEmptyIntersection = new Intersection(4, 4);
        sameSideIntersection = new Intersection(5, 5);
        oppositeSideIntersection = new Intersection(6, 6);

        Map<Intersection, Piece> pieces = Map.of(
                notEmptyIntersection, new Soldier(SIDE),
                sameSideIntersection, new Soldier(SIDE),
                oppositeSideIntersection, new Soldier(OPPOSITE_SIDE)
        );

        alivePieces = new AlivePieces(pieces);
    }

    @Nested
    class 좌표가_비어_있는지를_판단한다 {

        @Test
        void 비어_있는_좌표라면_true를_반환한다() {
            boolean result = alivePieces.isEmpty(emptyIntersection);

            assertThat(result).isTrue();
        }

        @Test
        void 상대_기물이_배치되어_있다면_false를_반환한다() {
            boolean result = alivePieces.isEmpty(oppositeSideIntersection);

            assertThat(result).isFalse();
        }

        @Test
        void 아군_기물이_배치되어_있다면_true를_반환한다() {
            boolean result = alivePieces.isEmpty(sameSideIntersection);

            assertThat(result).isFalse();
        }
    }

    @Nested
    class 좌표에_배치된_기물을_반환한다 {

        @Test
        void 비어_있는_좌표라면_null을_반환한다() {
            Piece piece = alivePieces.placedAt(emptyIntersection);

            assertThat(piece).isNull();
        }

        @Test
        void 비어_있지_않은_좌표라면_기물을_반환한다() {
            Piece piece = alivePieces.placedAt(notEmptyIntersection);

            assertThat(piece).isNotNull();
        }
    }

    @Nested
    class 같은_진영_기물이_배치되어_있는지를_판단한다 {

        @Test
        void 같은_진영_기물이_배치되어_있다면_true를_반환한다() {
            boolean result = alivePieces.placedSameSide(sameSideIntersection, SIDE);

            assertThat(result).isTrue();
        }

        @Test
        void 상대_진영_기물이_배치되어_있다면_false를_반환한다() {
            boolean result = alivePieces.placedSameSide(oppositeSideIntersection, SIDE);

            assertThat(result).isFalse();
        }

        @Test
        void 빈_좌표라면_false를_반환한다() {
            boolean result = alivePieces.placedSameSide(emptyIntersection, SIDE);

            assertThat(result).isFalse();
        }
    }

    @Nested
    class 같은_진영_기물이_안_배치되어_있는지를_판단한다 {

        @Test
        void 상대_진영_기물이_배치되어_있다면_true를_반환한다() {
            boolean result = alivePieces.placedNotSameSide(oppositeSideIntersection, SIDE);

            assertThat(result).isTrue();
        }

        @Test
        void 빈_좌표라면_true를_반환한다() {
            boolean result = alivePieces.placedNotSameSide(emptyIntersection, SIDE);

            assertThat(result).isTrue();
        }

        @Test
        void 같은_진영_기물이_배치되어_있다면_false를_반환한다() {
            boolean result = alivePieces.placedNotSameSide(sameSideIntersection, SIDE);

            assertThat(result).isFalse();
        }
    }

    @Nested
    class 상대_진영_기물이_배치되어_있는지를_판단한다 {

        @Test
        void 상대_진영_기물이_배치되어_있다면_true를_반환한다() {
            boolean result = alivePieces.placedOppositeSide(oppositeSideIntersection, SIDE);

            assertThat(result).isTrue();
        }

        @Test
        void 같은_진영_기물이_배치되어_있다면_false를_반환한다() {
            boolean result = alivePieces.placedOppositeSide(sameSideIntersection, SIDE);

            assertThat(result).isFalse();
        }

        @Test
        void 빈_좌표라면_false를_반환한다() {
            boolean result = alivePieces.placedOppositeSide(emptyIntersection, SIDE);

            assertThat(result).isFalse();
        }
    }
}
