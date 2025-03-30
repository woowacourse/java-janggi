package domain.board;

import domain.Team;
import domain.pieces.BoardStub;
import execptions.JanggiArgumentException;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public final class BoardTest {

    @Nested
    @DisplayName("기물을 이동할 때")
    class TestMakeMovementsThrowException {
        @Test
        @DisplayName("해당 경로로 이동할 수 없으면 예외를 던진다")
        void test_throwExceptionWhenPieceIsNotMovable() {
            // given
            Board board = BoardStub.generateBoard();
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalpoint = new BoardPoint(5, 0);

            // when
            assertThatThrownBy(() -> board.movePiece(startBoardPoint, arrivalpoint, Team.CHO))
                    .isInstanceOf(JanggiArgumentException.class)
                    .hasMessageContaining("해당 경로로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("해당 위치가 이동할 수 없는 위치면 예외를 던진다")
        void test_throwExceptionWhenPieceIsNotAbleToArrive() {
            // given
            Board board = BoardStub.generateBoard();
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalpoint = new BoardPoint(1, 1);

            // when
            assertThatThrownBy(() -> board.movePiece(startBoardPoint, arrivalpoint, Team.HAN))
                    .isInstanceOf(JanggiArgumentException.class)
                    .hasMessageContaining("아군 기물만 움직일 수 있습니다.");
        }

        @Test
        @DisplayName("이동할 기물이 존재하지 않으면 예외를 던진다")
        void test_NoPieceOnStartPoint() {
            // given
            Board board = BoardStub.generateBoard();
            BoardPoint startBoardPoint = new BoardPoint(1, 0);
            BoardPoint arrivalpoint = new BoardPoint(1, 1);

            // when & then
            assertThatThrownBy(() -> board.movePiece(startBoardPoint, arrivalpoint, Team.HAN))
                    .isInstanceOf(JanggiArgumentException.class)
                    .hasMessageContaining("출발점에 이동할 기물이 없습니다.");
        }

        private static Stream<Arguments> testCasesForUnableBoardPoints() {
            return Stream.of(
                    Arguments.of(new BoardPoint(0, 4), new BoardPoint(1, 3)),
                    Arguments.of(new BoardPoint(1, 3), new BoardPoint(0, 4)),
                    Arguments.of(new BoardPoint(1, 3), new BoardPoint(2, 4)),
                    Arguments.of(new BoardPoint(2, 4), new BoardPoint(1, 3)),
                    Arguments.of(new BoardPoint(1, 5), new BoardPoint(2, 4)),
                    Arguments.of(new BoardPoint(2, 4), new BoardPoint(1, 5))
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForUnableBoardPoints")
        @DisplayName("궁 내부에 이동 불가능한 경로라면 예외를 던진다")
        void test_UnableBoardPoints(BoardPoint startPoint, BoardPoint arrivalPoint) {
            // given
            Board board = BoardStub.generateBoard();

            // when & then
            assertThatThrownBy(() -> board.movePiece(startPoint, arrivalPoint, Team.HAN))
                    .isInstanceOf(JanggiArgumentException.class);
        }
    }

    @Test
    @DisplayName("팀의 점수를 계산한다")
    void test_CalculateScoreOf() {
        // given
        Board board = BoardStub.generateBoard();

        // when
        int score = board.calculateScoreOf(Team.CHO);

        // then
        assertThat(score).isEqualTo(72);
    }

}
