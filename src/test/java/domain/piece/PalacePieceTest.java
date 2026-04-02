package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.Vector;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PalacePieceTest {

    @Nested
    class 이동_가능한_위치를_판단한다 {

        private static final int DEFAULT_ROW = 5;
        private static final int DEFAULT_FILE = 5;
        private static final Side SIDE = Side.HAN;
        private static final Side OPPOSITE_SIDE = Side.CHO;
        private static final Soldier SAME_SIDE_PIECE = new Soldier(SIDE);
        private static final Soldier OPPOSITE_SIDE_PIECE = new Soldier(OPPOSITE_SIDE);
        private static final Intersection CURRENT_INTERSECTION = new Intersection(DEFAULT_ROW, DEFAULT_FILE);

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
            boolean canMove = palacePiece.canMove(CURRENT_INTERSECTION, sameSidePieceIntersection, alivePieces);

            // then
            assertThat(canMove).isFalse();
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
            boolean canMove = palacePiece.canMove(CURRENT_INTERSECTION, oppositeSidePieceIntersection, alivePieces);

            // then
            assertThat(canMove).isTrue();
        }

        @Test
        void 비어_있는_위치로는_이동할_수_있다() {
            // given
            PalacePiece palacePiece = new Guard(SIDE);

            Vector forward = SIDE.toForward();
            Intersection emptyIntersection = forward.next(CURRENT_INTERSECTION);
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            // when
            boolean canMove = palacePiece.canMove(CURRENT_INTERSECTION, emptyIntersection, emptyAlivePieces);

            // then
            assertThat(canMove).isTrue();
        }
    }
}
