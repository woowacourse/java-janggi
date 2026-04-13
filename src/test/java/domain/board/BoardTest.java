package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("장기 보드 테스트")
class BoardTest {

    private static final Intersection DEFAULT_INTERSECTION = new Intersection(5, 5);
    private static final Intersection DEFAULT_START_Position = new Intersection(6, 6);
    private static final Intersection DEFAULT_DESTINATION = new Intersection(7, 7);
    private static final Side DEFAULT_SIDE = Side.HAN;
    private static final Side SAME_SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;
    private static final Piece SAME_SIDE_PIECE = Piece.of(PieceType.SOLDIER, SAME_SIDE);
    private static final Piece OPPOSITE_SIDE_PIECE = Piece.of(PieceType.SOLDIER, OPPOSITE_SIDE);

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
            }).isInstanceOf(IllegalArgumentException.class);
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
            }).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class 기물을_이동시킨다 {

        @Test
        void 이동시킬_기물이_없다면_예외를_던진다() {
            // given
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            Board emptyBoard = new Board(emptyAlivePieces);

            // when and then
            assertThatThrownBy(() -> {
                emptyBoard.movePiece(
                        DEFAULT_START_Position,
                        DEFAULT_DESTINATION,
                        DEFAULT_SIDE
                );
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 이동시킬_기물이_다른_진영이라면_예외를_던진다() {
            // given
            Intersection oppositePieceIntersection = DEFAULT_INTERSECTION;
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    oppositePieceIntersection, OPPOSITE_SIDE_PIECE
            ));

            Board board = new Board(alivePieces);

            // when and then
            assertThatThrownBy(() -> {
                board.movePiece(
                        oppositePieceIntersection,
                        DEFAULT_DESTINATION,
                        SAME_SIDE
                );
            }).isInstanceOf(IllegalArgumentException.class);
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

            // when and then
            assertThatThrownBy(() -> {
                board.movePiece(
                        startIntersection,
                        unreachableDestination,
                        SAME_SIDE
                );
            }).isInstanceOf(IllegalArgumentException.class);
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

            // when
            board.movePiece(
                    startIntersection,
                    reachableDestination,
                    SAME_SIDE
            );

            // then
            Piece destinationPiece = board.placedAt(reachableDestination);

            assertThat(destinationPiece).isEqualTo(SAME_SIDE_PIECE);
        }
    }

    @DisplayName("왕이 잡혔는지 검증")
    @Nested
    class 왕이_잡혔는지_검증 {

        @DisplayName("두 진영 모두 왕이 없는 경우")
        @ParameterizedTest(name = "{0} 진영의 왕이 잡혔음을 판단")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 두_진영_모두_왕이_없는_경우(Side side) {
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
            Board emptyBoard = new Board(emptyAlivePieces);

            boolean generalCaptured = emptyBoard.isGeneralCaptured(side);

            assertThat(generalCaptured).isTrue();
        }

        @DisplayName("한 진영만 왕이 없는 경우")
        @Test
        void 한_진영만_왕이_없는_경우() {
            Side capturedSide = Side.HAN;
            Side noCapturedSide = Side.CHO;
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    new Intersection(5, 5), Piece.of(PieceType.GENERAL, noCapturedSide)
            ));
            Board board = new Board(alivePieces);

            assertThat(board.isGeneralCaptured(capturedSide)).isTrue();
            assertThat(board.isGeneralCaptured(noCapturedSide)).isFalse();
        }

        @DisplayName("두 진영 모두 왕이 있는 경우")
        @Test
        void 두_진영_모두_왕이_있는_경우() {
            Side cho = Side.CHO;
            Side han = Side.HAN;
            Piece choGeneral = Piece.of(PieceType.GENERAL, cho);
            Piece hanGeneral = Piece.of(PieceType.GENERAL, han);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    new Intersection(3, 3), choGeneral,
                    new Intersection(5, 5), hanGeneral
            ));
            Board board = new Board(alivePieces);

            assertThat(board.isGeneralCaptured(cho)).isFalse();
            assertThat(board.isGeneralCaptured(han)).isFalse();
        }
    }

    @DisplayName("진영별 기물 점수 합계를 계산한다")
    @Test
    void 진영별_기물_점수_합계를_계산한다() {
        // given
        AlivePieces alivePieces = new AlivePieces(Map.of(
                new Intersection(5, 5), Piece.of(PieceType.CHARIOT, Side.HAN)
        ));
        Board board = new Board(alivePieces);

        // when
        int totalPointOfHan = board.calculatePiecePointOf(Side.HAN);
        int totalPointOfCho = board.calculatePiecePointOf(Side.CHO);

        // then
        assertThat(totalPointOfHan).isEqualTo(13);
        assertThat(totalPointOfCho).isEqualTo(0);
    }
}
