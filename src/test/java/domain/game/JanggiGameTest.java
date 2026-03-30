package domain.game;

import static domain.util.AssertUtils.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.Intersection;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    private static final String HAN_TURN_MESSAGE = "지금은 " + Side.HAN + "의 차례입니다.";
    private static final String CHO_TURN_MESSAGE = "지금은 " + Side.CHO + "의 차례입니다.";

    private static final Piece CHO_PIECE = new Soldier(Side.CHO);
    private static final Intersection CHO_START_INTERSECTION = new Intersection(5, 5);
    private static final Intersection CHO_FIRST_DESTINATION = new Intersection(4, 5);
    private static final Intersection CHO_SECOND_DESTINATION = new Intersection(3, 5);

    private static final Piece HAN_PIECE = new Soldier(Side.HAN);
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
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(CHO_TURN_MESSAGE);
        }

        @Test
        void 첫_수가_초면_성공한다() {
            // given
            Board board = new Board(alivePieces);
            JanggiGame janggiGame = new JanggiGame(board);

            // when and then
            assertThatNoException(() -> janggiGame.movePiece(CHO_START_INTERSECTION, CHO_FIRST_DESTINATION, Side.CHO));
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
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(HAN_TURN_MESSAGE);
        }

        @Test
        void 각_진영이_교대로_기물을_이동하면_성공한다() {
            // given
            Board board = new Board(alivePieces);
            JanggiGame janggiGame = new JanggiGame(board);

            // when and then
            janggiGame.movePiece(CHO_START_INTERSECTION, CHO_FIRST_DESTINATION, Side.CHO);
            assertThatNoException(() -> janggiGame.movePiece(HAN_START_INTERSECTION, HAN_FIRST_DESTINATION, Side.HAN));
        }
    }
}
