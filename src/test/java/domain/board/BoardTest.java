package domain.board;

import domain.Team;
import domain.pieces.BoardStub;
import execptions.JanggiArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class BoardTest {

    @Nested
    @DisplayName("기물을 이동할 때")
    class TestMakeMovementsThrowException {
        @Test
        @DisplayName("해당 경로로 이동할 수 없으면 예외를 던진다")
        void test_throwExceptionWhenPieceIsNotMovable() {
            // given
            Board board = BoardStub.generateBoard();
            Point startPoint = new Point(0, 0);
            Point arrivalpoint = new Point(5, 0);

            // when
            assertThatThrownBy(() -> board.movePiece(startPoint, arrivalpoint, Team.CHO))
                    .isInstanceOf(JanggiArgumentException.class)
                    .hasMessageContaining("해당 경로로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("해당 위치가 이동할 수 없는 위치면 예외를 던진다")
        void test_throwExceptionWhenPieceIsNotAbleToArrive() {
            // given
            Board board = BoardStub.generateBoard();
            Point startPoint = new Point(0, 0);
            Point arrivalpoint = new Point(1, 1);

            // when
            assertThatThrownBy(() -> board.movePiece(startPoint, arrivalpoint, Team.HAN))
                    .isInstanceOf(JanggiArgumentException.class)
                    .hasMessageContaining("아군 기물만 움직일 수 있습니다.");
        }

        @Test
        @DisplayName("이동할 기물이 존재하지 않으면 예외를 던진다")
        void test_NoPieceOnStartPoint() {
            // given
            Board board = BoardStub.generateBoard();
            Point startPoint = new Point(1, 0);
            Point arrivalpoint = new Point(1, 1);

            // when & then
            assertThatThrownBy(() -> board.movePiece(startPoint, arrivalpoint, Team.HAN))
                    .isInstanceOf(JanggiArgumentException.class)
                    .hasMessageContaining("출발점에 이동할 기물이 없습니다.");
        }
    }

}
