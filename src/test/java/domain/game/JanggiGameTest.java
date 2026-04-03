package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.Intersection;
import domain.piece.AlivePieces;
import domain.piece.Soldier;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    private static final Intersection CHO_PIECE_INTERSECTION = new Intersection(5, 5);
    private static final Intersection HAN_PIECE_INTERSECTION = new Intersection(3, 3);
    private static final Intersection CHO_DESTINATION = new Intersection(4, 5);

    private AlivePieces alivePieces;

    @BeforeEach
    void setUp() {
        alivePieces = new AlivePieces(Map.of(
                CHO_PIECE_INTERSECTION, new Soldier(Side.CHO),
                HAN_PIECE_INTERSECTION, new Soldier(Side.HAN)
        ));
    }

    @Test
    void 초_진영의_차례로_시작한다() {
        // given
        Board board = new Board(alivePieces);
        JanggiGame janggiGame = new JanggiGame(board);

        // when
        Side currentTurn = janggiGame.getCurrentTurn();

        // then
        assertThat(currentTurn).isEqualTo(Side.CHO);
    }

    @Test
    void 기물을_이동하고_나면_차례를_넘긴다() {
        // given
        Board board = new Board(alivePieces);
        JanggiGame janggiGame = new JanggiGame(board);

        // when
        Side turnBeforeMove = janggiGame.getCurrentTurn();
        janggiGame.movePiece(CHO_PIECE_INTERSECTION, CHO_DESTINATION);
        Side turnAfterMove = janggiGame.getCurrentTurn();

        // then
        assertThat(turnAfterMove).isNotEqualTo(turnBeforeMove);
    }

    @Nested
    class 기물이_이동_가능한_위치를_반환한다 {

        @Test
        void 다른_진영의_기물을_선택하면_예외를_던진다() {
            // given
            Board board = new Board(alivePieces);
            JanggiGame janggiGame = new JanggiGame(board);

            // when and then
            assertThatThrownBy(() ->
                    janggiGame.getMovableIntersections(HAN_PIECE_INTERSECTION)
            )
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("같은 진영의 기물을 선택해야 합니다.");
        }

        @Test
        void 본인_진영의_기물을_선택하면_이동_가능한_위치를_반환한다() {
            Board board = new Board(alivePieces);
            JanggiGame janggiGame = new JanggiGame(board);

            List<Intersection> movable = janggiGame.getMovableIntersections(CHO_PIECE_INTERSECTION);

            assertThat(movable).containsExactlyInAnyOrder(
                    CHO_DESTINATION,
                    new Intersection(5, 4),
                    new Intersection(5, 6)
            );
        }
    }
}
