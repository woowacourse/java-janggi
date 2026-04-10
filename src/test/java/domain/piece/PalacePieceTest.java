package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.board.Palace;
import domain.game.Side;
import domain.movement.Vector;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PalacePieceTest {

    @Nested
    class 이동_가능한_위치를_판단한다 {

        private static final Side SIDE = Side.HAN;
        private static final Side OPPOSITE_SIDE = Side.CHO;
        private static final Soldier SAME_SIDE_PIECE = new Soldier(SIDE);
        private static final Soldier OPPOSITE_SIDE_PIECE = new Soldier(OPPOSITE_SIDE);
        private static final Intersection PALACE_CENTER = new Intersection(2, 5);
        private static final Intersection CURRENT_INTERSECTION = PALACE_CENTER;

        @Test
        void 아군_기물이_있는_위치로는_이동할_수_없다() {
            // given
            PalacePiece palacePiece = new Guard(SIDE);

            Vector forward = SIDE.toForward();
            Intersection sameSidePieceIntersection = forward.next(CURRENT_INTERSECTION);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    sameSidePieceIntersection, SAME_SIDE_PIECE
            ));

            // when
            List<Intersection> movableIntersections = palacePiece.movableIntersections(
                    CURRENT_INTERSECTION,
                    alivePieces
            );

            // then
            assertThat(movableIntersections).doesNotContain(sameSidePieceIntersection);
        }

        @Test
        void 보드_범위_밖으로는_이동할_수_없다() {
            // given
            PalacePiece palacePiece = new Guard(SIDE);

            Intersection borderlineIntersection = new Intersection(1, 1);
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            // when
            List<Intersection> movableIntersections = palacePiece.movableIntersections(
                    borderlineIntersection,
                    emptyAlivePieces
            );

            // then
            boolean movableOutOfBoard = movableIntersections.stream()
                    .anyMatch(Intersection::isOutOfBoard);

            assertThat(movableOutOfBoard).isFalse();
        }

        @Test
        void 상대_기물이_있는_위치로는_이동할_수_있다() {
            // given
            PalacePiece palacePiece = new Guard(SIDE);

            Vector forward = SIDE.toForward();
            Intersection oppositeSidePieceIntersection = forward.next(CURRENT_INTERSECTION);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    oppositeSidePieceIntersection, OPPOSITE_SIDE_PIECE
            ));

            // when
            List<Intersection> movableIntersections = palacePiece.movableIntersections(
                    CURRENT_INTERSECTION,
                    alivePieces
            );

            // then
            assertThat(movableIntersections).contains(oppositeSidePieceIntersection);
        }

        @Test
        void 비어_있는_위치로는_이동할_수_있다() {
            // given
            PalacePiece palacePiece = new Guard(SIDE);

            Vector forward = SIDE.toForward();
            Intersection emptyIntersection = forward.next(CURRENT_INTERSECTION);
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            // when
            List<Intersection> movableIntersections = palacePiece.movableIntersections(
                    CURRENT_INTERSECTION,
                    emptyAlivePieces
            );

            // then
            assertThat(movableIntersections).contains(emptyIntersection);
        }

        @Test
        void 궁성_바깥으로는_이동할_수_없다() {
            // given
            PalacePiece palacePiece = new Guard(SIDE);

            Intersection palaceBorderline = new Intersection(3, 5);
            Intersection outOfPalace = new Intersection(4, 5);
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            // when
            List<Intersection> movableIntersections = palacePiece.movableIntersections(
                    palaceBorderline,
                    emptyAlivePieces
            );

            // then
            assertThat(movableIntersections).doesNotContain(outOfPalace);
        }

        @Test
        void 궁성의_대각선으로_이동할_수_있다() {
            // given
            PalacePiece palacePiece = new Guard(SIDE);
            Palace palace = Palace.getInstance();

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    PALACE_CENTER, palacePiece
            ));

            List<Vector> palaceDiagonalVectors = palace.getDiagonalVectors(PALACE_CENTER);
            List<Intersection> expectedPalaceDestinations = palaceDiagonalVectors.stream()
                    .map(vector -> vector.next(PALACE_CENTER))
                    .toList();

            // when
            List<Intersection> movableIntersections = palacePiece.movableIntersections(PALACE_CENTER, alivePieces);

            // then
            assertThat(movableIntersections).containsAll(expectedPalaceDestinations);
        }
    }
}
