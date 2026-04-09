package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Side;
import domain.movement.Move;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    private static final String EMPTY_INTERSECTION_MESSAGE = "기물이 있는 지점을 선택해야 합니다.";
    private static final String DIFFERENT_SIDE_MESSAGE = "같은 진영의 기물을 선택해야 합니다.";

    private static final Intersection DEFAULT_INTERSECTION = new Intersection(5, 5);
    private static final Intersection DEFAULT_START_POINT = new Intersection(6, 6);
    private static final Intersection DEFAULT_DESTINATION = new Intersection(7, 7);
    private static final Side DEFAULT_SIDE = Side.HAN;
    private static final Side SAME_SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;
    private static final Piece SAME_SIDE_PIECE = new Soldier(SAME_SIDE);
    private static final Piece OPPOSITE_SIDE_PIECE = new Soldier(OPPOSITE_SIDE);

    @Nested
    class 기물이_이동_가능한_지점들을_반환한다 {

        @Test
        void 선택한_지점이_비었다면_예외를_던진다() {
            // given
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
            Board emptyBoard = new Board(emptyAlivePieces);

            // when and then
            assertThatThrownBy(() -> {
                emptyBoard.getMovableIntersections(
                        DEFAULT_INTERSECTION,
                        DEFAULT_SIDE
                );
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(EMPTY_INTERSECTION_MESSAGE);
        }

        @Test
        void 선택한_기물이_다른_진영이라면_예외를_던진다() {
            // given
            Intersection oppositePieceIntersection = DEFAULT_INTERSECTION;
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    oppositePieceIntersection, OPPOSITE_SIDE_PIECE
            ));

            Board board = new Board(alivePieces);

            // when and then
            assertThatThrownBy(() -> {
                board.getMovableIntersections(
                        oppositePieceIntersection,
                        SAME_SIDE
                );
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(DIFFERENT_SIDE_MESSAGE);
        }
    }

    @Nested
    class 기물을_이동시킨다 {

        @Test
        void 이동시킬_기물이_없다면_예외를_던진다() {
            // given
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
            Board emptyBoard = new Board(emptyAlivePieces);

            Move moveFromEmpty = new Move(DEFAULT_START_POINT, DEFAULT_DESTINATION);

            // when and then
            assertThatThrownBy(() -> emptyBoard.movePiece(moveFromEmpty, DEFAULT_SIDE))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(EMPTY_INTERSECTION_MESSAGE);
        }

        @Test
        void 이동시킬_기물이_다른_진영이라면_예외를_던진다() {
            // given
            Intersection oppositePieceIntersection = DEFAULT_INTERSECTION;
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    oppositePieceIntersection, OPPOSITE_SIDE_PIECE
            ));

            Board board = new Board(alivePieces);

            Move moveOppositeSidePiece = new Move(oppositePieceIntersection, DEFAULT_DESTINATION);

            // when and then
            assertThatThrownBy(() -> board.movePiece(moveOppositeSidePiece, SAME_SIDE))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(DIFFERENT_SIDE_MESSAGE);
        }

        @Test
        void 목적지로_이동할_수_없다면_예외를_던진다() {
            // given
            Intersection startIntersection = new Intersection(1, 1);
            Intersection unreachableDestination = new Intersection(10, 10);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    startIntersection, SAME_SIDE_PIECE
            ));

            Board board = new Board(alivePieces);

            Move moveToUnreachable = new Move(startIntersection, unreachableDestination);

            // when and then
            assertThatThrownBy(() -> board.movePiece(moveToUnreachable, SAME_SIDE))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("도착 가능한 지점을 선택해야 합니다.");
        }

        @Test
        void 이동시킬_기물이_이동_가능한_아군_기물이면_이동시킨다() {
            // given
            Intersection startIntersection = new Intersection(1, 1);
            Intersection reachableDestination = new Intersection(1, 2);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    startIntersection, SAME_SIDE_PIECE
            ));

            Board board = new Board(alivePieces);

            Move moveSameSidePiece = new Move(startIntersection, reachableDestination);

            // when
            board.movePiece(moveSameSidePiece, SAME_SIDE);

            // then
            Map<Intersection, Piece> pieces = board.getPieces();

            assertThat(pieces.get(reachableDestination)).isEqualTo(SAME_SIDE_PIECE);
        }
    }
}
