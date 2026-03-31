package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AlivePiecesTest {

    @Test
    void a() {
        AlivePieces alivePieces = new AlivePieces(Map.of());
        Piece placed = alivePieces.placedAt(new Intersection(1, 1));
    }

    @DisplayName("기물의 존재 여부 검증")
    @Nested
    class 기물의_존재_여부_검증 {

        private static final Intersection NON_EMPTY_INTERSECTION = new Intersection(5, 5);
        private static final Intersection EMPTY_INTERSECTION = new Intersection(3, 3);
        private static final Piece DEFAULT_PIECE = new Piece(PieceType.SOLDIER, Side.CHO);
        private static final AlivePieces alivePieces = new AlivePieces(Map.of(
                NON_EMPTY_INTERSECTION, DEFAULT_PIECE
        ));

        @DisplayName("비어 있는 곳 검증")
        @Test
        void 비어_있는_곳_검증() {
            assertThat(alivePieces.isEmpty(EMPTY_INTERSECTION)).isTrue();
            assertThat(alivePieces.isNotEmpty(EMPTY_INTERSECTION)).isFalse();
        }

        @DisplayName("비어 있지 않은 곳 검증")
        @Test
        void 비어_있지_않은_곳_검증() {
            assertThat(alivePieces.isNotEmpty(NON_EMPTY_INTERSECTION)).isTrue();
            assertThat(alivePieces.isEmpty(NON_EMPTY_INTERSECTION)).isFalse();
        }

        @DisplayName("존재하는 기물 검증")
        @Test
        void 존재하는_기물_검증() {
            Piece placed = alivePieces.placedAt(NON_EMPTY_INTERSECTION);

            assertThat(placed).isSameAs(DEFAULT_PIECE);
        }
    }

    @DisplayName("놓여 있는 기물의 존재 검증")
    @Nested
    class 놓여_있는_기물의_존재_검증 {

        @DisplayName("같은 진영 검증")
        @Test
        void 같은_진영_검증() {
            Piece choPiece = new Piece(PieceType.SOLDIER, Side.CHO);
            Intersection targetIntersection = new Intersection(5, 5);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    targetIntersection, choPiece
            ));

            boolean placedSameSide = alivePieces.placedSameSide(targetIntersection, Side.CHO);
            boolean placedNotSameSide = alivePieces.placedNotSameSide(targetIntersection, Side.CHO);

            assertThat(placedSameSide).isTrue();
            assertThat(placedNotSameSide).isFalse();
        }

        @DisplayName("상대 진영 검증")
        @Test
        void 상대_진영_검증() {
            Piece choPiece = new Piece(PieceType.SOLDIER, Side.CHO);
            Intersection targetIntersection = new Intersection(5, 5);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    targetIntersection, choPiece
            ));

            boolean placedSameSide = alivePieces.placedSameSide(targetIntersection, Side.HAN);
            boolean placedNotSameSide = alivePieces.placedNotSameSide(targetIntersection, Side.HAN);

            assertThat(placedSameSide).isFalse();
            assertThat(placedNotSameSide).isTrue();
        }
    }

    @DisplayName("기물의 위치 이동 검증")
    @Test
    void 기물의_위치_이동_검증() {
        Piece targetPiece = new Piece(PieceType.SOLDIER, Side.CHO);
        Intersection startIntersection = new Intersection(5, 5);
        Intersection destination = new Intersection(3, 3);
        AlivePieces alivePieces = new AlivePieces(Map.of(
                startIntersection, targetPiece
        ));

        alivePieces.replace(startIntersection, destination);
        Piece placed = alivePieces.placedAt(destination);

        assertThat(placed).isSameAs(targetPiece);
    }
}
