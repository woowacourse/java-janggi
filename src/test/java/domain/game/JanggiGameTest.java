package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.Intersection;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class JanggiGameTest {

    private static final Piece CHO_PIECE = Piece.of(PieceType.SOLDIER, Side.CHO);
    private static final Intersection CHO_START_INTERSECTION = new Intersection(5, 5);
    private static final Intersection CHO_FIRST_DESTINATION = new Intersection(4, 5);
    private static final Intersection CHO_SECOND_DESTINATION = new Intersection(3, 5);

    private static final Piece HAN_PIECE = Piece.of(PieceType.SOLDIER, Side.HAN);
    private static final Intersection HAN_START_INTERSECTION = new Intersection(3, 3);
    private static final Intersection HAN_FIRST_DESTINATION = new Intersection(4, 3);

    private AlivePieces alivePieces;

    @BeforeEach
    void setUp() {
        alivePieces = new AlivePieces(Map.of(
                CHO_START_INTERSECTION, CHO_PIECE,
                HAN_START_INTERSECTION, HAN_PIECE
        ));
    }

    @Nested
    class 첫_수는_초여야_한다 {

        @Test
        void 첫_수가_초가_아니면_예외를_던진다() {
            // given
            Board board = new Board(alivePieces);
            JanggiGame janggiGame = new JanggiGame(board);

            // when and then
            assertThatThrownBy(() -> janggiGame.movePiece(HAN_START_INTERSECTION, HAN_FIRST_DESTINATION, Side.HAN))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 첫_수가_초면_성공한다() {
            // given
            Board board = new Board(alivePieces);
            JanggiGame janggiGame = new JanggiGame(board);

            // when and then
            assertThatNoException()
                    .isThrownBy(() -> janggiGame.movePiece(CHO_START_INTERSECTION, CHO_FIRST_DESTINATION, Side.CHO));
        }
    }

    @Nested
    class 각_진영은_교대로_기물을_이동해야_한다 {

        @Test
        void 같은_진영이_연속해서_기물을_이동하려고_하면_예외를_던진다() {
            // given
            Board board = new Board(alivePieces);
            JanggiGame janggiGame = new JanggiGame(board);

            // when and then
            janggiGame.movePiece(
                    CHO_START_INTERSECTION,
                    CHO_FIRST_DESTINATION,
                    Side.CHO
            );
            assertThatThrownBy(() -> {
                janggiGame.movePiece(
                        CHO_FIRST_DESTINATION,
                        CHO_SECOND_DESTINATION,
                        Side.CHO
                );
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 각_진영이_교대로_기물을_이동하면_성공한다() {
            // given
            Board board = new Board(alivePieces);
            JanggiGame janggiGame = new JanggiGame(board);

            // when and then
            janggiGame.movePiece(CHO_START_INTERSECTION, CHO_FIRST_DESTINATION, Side.CHO);
            assertThatNoException()
                    .isThrownBy(() -> janggiGame.movePiece(HAN_START_INTERSECTION, HAN_FIRST_DESTINATION, Side.HAN));
        }
    }

    @DisplayName("왕의 잡힘 여부에 따라 게임 종료를 판단한다")
    @Nested
    class 왕의_잡힘_여부에_따라_게임_종료를_판단한다 {

        @DisplayName("왕이 하나라도 존재하지 않으면 게임이 종료된 상태이다")
        @ParameterizedTest(name = "{0} 진영의 왕이 존재하지 않는 경우")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 왕이_하나라도_존재하지_않으면_게임이_종료된_상태이다(Side side) {
            AlivePieces piecesWithOnlyOneGeneral = new AlivePieces(Map.of(
                    new Intersection(5, 5), Piece.of(PieceType.GENERAL, side)
            ));
            Board board = new Board(piecesWithOnlyOneGeneral);
            JanggiGame janggiGame = new JanggiGame(board);

            boolean gameFinished = janggiGame.isFinished();

            assertThat(gameFinished).isTrue();
        }

        @DisplayName("왕이 모두 존재하면 게임이 종료되지 않은 상태이다")
        @Test
        void 왕이_모두_존재하면_게임이_종료되지_않은_상태이다() {
            AlivePieces piecesWithBothGenerals = new AlivePieces(Map.of(
                    new Intersection(5, 5), Piece.of(PieceType.GENERAL, Side.CHO),
                    new Intersection(3, 3), Piece.of(PieceType.GENERAL, Side.HAN)
            ));
            Board board = new Board(piecesWithBothGenerals);
            JanggiGame janggiGame = new JanggiGame(board);

            boolean gameFinished = janggiGame.isFinished();

            assertThat(gameFinished).isFalse();
        }
    }

    @DisplayName("진영별 점수 합계를 계산한다")
    @Test
    void 진영별_점수_합계를_계산한다() {
        // given
        JanggiGame janggiGame = new JanggiGame(new Board(alivePieces));
        int expectedPointWithOnlyPieces = 2;
        double expectedBonusPointOfHan = 1.5;

        // when
        double pointOfCho = janggiGame.calculatePointOf(Side.CHO);
        double pointOfHan = janggiGame.calculatePointOf(Side.HAN);

        // then
        assertThat(pointOfCho).isEqualTo(expectedPointWithOnlyPieces);
        assertThat(pointOfHan).isEqualTo(expectedPointWithOnlyPieces + expectedBonusPointOfHan);
    }
}
