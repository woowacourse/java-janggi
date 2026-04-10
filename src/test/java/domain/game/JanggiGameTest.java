package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.Intersection;
import domain.movement.Move;
import domain.piece.AlivePieces;
import domain.piece.General;
import domain.piece.Soldier;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class JanggiGameTest {

    private static final Intersection DEFAULT_INTERSECTION = new Intersection(4, 4);
    private static final Intersection CHO_GENERAL_INTERSECTION = new Intersection(9, 5);
    private static final Intersection HAN_GENERAL_INTERSECTION = new Intersection(2, 5);
    private static final Intersection CHO_PIECE_INTERSECTION = new Intersection(5, 5);
    private static final Intersection HAN_PIECE_INTERSECTION = new Intersection(3, 3);
    private static final Intersection CHO_DESTINATION = new Intersection(4, 5);

    private AlivePieces allGeneralAlivePieces;
    private AlivePieces oneGeneralDeadPieces;

    @BeforeEach
    void setUp() {
        allGeneralAlivePieces = new AlivePieces(Map.of(
                CHO_PIECE_INTERSECTION, new Soldier(Side.CHO),
                HAN_PIECE_INTERSECTION, new Soldier(Side.HAN),
                CHO_GENERAL_INTERSECTION , new General(Side.CHO),
                HAN_GENERAL_INTERSECTION, new General(Side.HAN)
        ));
        oneGeneralDeadPieces = new AlivePieces(Map.of(
                new Intersection(5, 5), new General(Side.HAN)
        ));
    }

    @Test
    void 초_진영의_차례로_시작한다() {
        // given
        Board board = new Board(allGeneralAlivePieces);
        JanggiGame janggiGame = new JanggiGame(board);

        // when
        Side currentTurn = janggiGame.getCurrentTurn();

        // then
        assertThat(currentTurn).isEqualTo(Side.CHO);
    }

    @Test
    void 기물을_이동하고_나면_차례를_넘긴다() {
        // given
        Board board = new Board(allGeneralAlivePieces);
        JanggiGame janggiGame = new JanggiGame(board);
        Move move = new Move(CHO_PIECE_INTERSECTION, CHO_DESTINATION);

        Side turnBeforeMove = janggiGame.getCurrentTurn();

        // when
        janggiGame.movePiece(move);

        // then
        Side turnAfterMove = janggiGame.getCurrentTurn();

        assertThat(turnAfterMove).isNotEqualTo(turnBeforeMove);
    }

    @Nested
    class 기물이_이동_가능한_위치를_반환한다 {

        @Test
        void 다른_진영의_기물을_선택하면_예외를_던진다() {
            // given
            Board board = new Board(allGeneralAlivePieces);
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
            Board board = new Board(allGeneralAlivePieces);
            JanggiGame janggiGame = new JanggiGame(board);

            List<Intersection> movable = janggiGame.getMovableIntersections(CHO_PIECE_INTERSECTION);

            assertThat(movable).containsExactlyInAnyOrder(
                    CHO_DESTINATION,
                    new Intersection(5, 4),
                    new Intersection(5, 6)
            );
        }
    }

    @Nested
    class 게임이_진행_중인지_판단한다 {

        @Test
        void 모든_진영의_궁이_살아_있다면_게임_진행_중이라고_판단한다() {
            // given
            JanggiGame janggiGame = new JanggiGame(new Board(allGeneralAlivePieces));

            // when
            boolean playing = janggiGame.isPlaying();

            // then
            assertThat(playing).isTrue();
        }

        @Test
        void 어느_진영이든_궁이_없다면_게임_종료라고_판단한다() {
            // given
            JanggiGame janggiGame = new JanggiGame(new Board(oneGeneralDeadPieces));

            // when
            boolean playing = janggiGame.isPlaying();

            // then
            assertThat(playing).isFalse();
        }
    }

    @Nested
    class 승자를_판단한다 {

        @Test
        void 게임이_진행_중이라면_예외를_던진다() {
            // given
            JanggiGame playingJanggiGame = new JanggiGame(new Board(allGeneralAlivePieces));

            // when and then
            assertThatThrownBy(playingJanggiGame::getWinner)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("승자는 게임 종료 이후에 조회할 수 있습니다.");
        }

        @ParameterizedTest
        @EnumSource(Side.class)
        void 궁이_살아있는_진영을_승자로_취급한다(Side generalAliveSide) {
            // given
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    DEFAULT_INTERSECTION, new General(generalAliveSide)
            ));
            JanggiGame janggiGame = new JanggiGame(new Board(alivePieces));

            // when
            Side winner = janggiGame.getWinner();

            // then
            assertThat(winner).isEqualTo(generalAliveSide);
        }
    }
}
