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
            JanggiGame janggiGame = JanggiGame.create(board);

            // when and then
            assertThatThrownBy(() -> janggiGame.movePiece(HAN_START_INTERSECTION, HAN_FIRST_DESTINATION, Side.HAN))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 첫_수가_초면_성공한다() {
            // given
            Board board = new Board(alivePieces);
            JanggiGame janggiGame = JanggiGame.create(board);

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
            JanggiGame janggiGame = JanggiGame.create(board);

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
            JanggiGame janggiGame = JanggiGame.create(board);

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
        @ParameterizedTest(name = "{0} 진영의 왕만 존재하는 경우")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 왕이_하나라도_존재하지_않으면_게임이_종료된_상태이다(Side generalUncapturedSide) {
            JanggiGame janggiGame = JanggiGameFixture
                    .create_game_with_sufficient_points_and_one_general_captured(generalUncapturedSide);

            boolean gameFinished = janggiGame.isFinished();

            assertThat(gameFinished).isTrue();
        }

        @DisplayName("왕이 모두 존재하면 게임이 종료되지 않은 상태이다")
        @Test
        void 왕이_모두_존재하면_게임이_종료되지_않은_상태이다() {
            JanggiGame janggiGame =
                    JanggiGameFixture.create_game_with_sufficient_points_and_both_general_uncaptured();

            boolean gameFinished = janggiGame.isFinished();

            assertThat(gameFinished).isFalse();
        }
    }

    @DisplayName("양측 진영의 점수에 따라 게임 종료를 판단한다")
    @Nested
    class 양측_진영_점수에_따라_게임_종료_판단 {

        @DisplayName("양측 진영의 점수가 모두 30점 미만이라면 왕이 잡히지 않았어도 종료한다")
        @Test
        void 양측_진영_점수_30점_미만이면_게임이_종료된다() {
            JanggiGame janggiGame =
                    JanggiGameFixture.create_game_with_insufficient_points_and_both_general_uncaptured();

            assertThat(janggiGame.isFinished()).isTrue();
        }

        @DisplayName("한 쪽 진영이라도 30점 이상이라면 종료되지 않는다")
        @ParameterizedTest(name = "{0} 진영이 30점 이상이므로 종료되지 않음")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 한_쪽_진영_30점_이상이면_종료_아님(Side sufficientPointsSide) {
            JanggiGame janggiGame = JanggiGameFixture
                    .create_game_with_sufficient_points_only_one_side(sufficientPointsSide);

            assertThat(janggiGame.isFinished()).isFalse();
        }
    }


    @DisplayName("승자 판정")
    @Nested
    class 승자_판정 {

        @DisplayName("게임이 종료되지 않았다면 승자를 판정할 수 없다")
        @Test
        void 게임이_종료되지_않았는데_승자를_판정하려고_하면_예외를_던진다() {
            JanggiGame janggiGame =
                    JanggiGameFixture.create_game_with_sufficient_points_and_both_general_uncaptured();

            assertThatThrownBy(janggiGame::determineResult)
                    .isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("왕이 잡혔다면 점수에 관계없이 왕을 잡은 진영(= 잡히지 않은 진영)이 승리한다")
        @ParameterizedTest(name = "왕이 잡히지 않은 {0}이 승리")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 왕이_잡힌_경우_승자_판정(Side generalUncapturedSide) {
            JanggiGame janggiGame = JanggiGameFixture
                    .create_game_with_sufficient_points_and_one_general_captured(generalUncapturedSide);

            GameResult gameResult = janggiGame.determineResult();

            assertThat(gameResult.winner()).isEqualTo(generalUncapturedSide);
        }

        @DisplayName("왕이 잡히지 않았다면 점수가 높은 잡은 진영이 승리한다")
        @ParameterizedTest(name = "점수가 높은 {0}이 승리")
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 점수로_승패_판정(Side higherScoreSide) {
            JanggiGame janggiGame = JanggiGameFixture
                    .create_game_with_insufficient_points_and_both_general_uncaptured(higherScoreSide);

            GameResult gameResult = janggiGame.determineResult();

            assertThat(gameResult.winner()).isEqualTo(higherScoreSide);
        }

        @DisplayName("기물의 점수 합이 동일하다면 HAN이 보너스 점수를 받기 때문에 승리한다")
        @Test
        void HAN이_보너스_점수로_승리() {
            JanggiGame janggiGame = JanggiGameFixture
                    .create_game_with_insufficient_same_points_and_both_general_uncaptured();

            GameResult gameResult = janggiGame.determineResult();

            assertThat(gameResult.winner()).isEqualTo(Side.HAN);
        }
    }

    @DisplayName("진영별 점수 합계를 계산한다")
    @Test
    void 진영별_점수_합계를_계산한다() {
        // given
        JanggiGame janggiGame = JanggiGame.create(new Board(alivePieces));
        int expectedPointWithOnlyPieces = 2;
        double expectedBonusPointOfHan = 1.5;

        // when
        double pointOfCho = janggiGame.calculateScoreOf(Side.CHO);
        double pointOfHan = janggiGame.calculateScoreOf(Side.HAN);

        // then
        assertThat(pointOfCho).isEqualTo(expectedPointWithOnlyPieces);
        assertThat(pointOfHan).isEqualTo(expectedPointWithOnlyPieces + expectedBonusPointOfHan);
    }
}
