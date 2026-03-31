package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SaTest {

    private Sa sa;
    private Board board;

    @BeforeEach
    void setUp() {
        board = Board.createInitialBoard();
        sa = new Sa(TeamType.HAN);
    }

    @ParameterizedTest
    @DisplayName("사는 상하좌우 및 대각선으로 한 칸 이동할 수 있다.")
    @CsvSource({
            "4, 1, 4, 2",
            "4, 1, 5, 1",
            "4, 1, 3, 1",
            "4, 1, 5, 2",
            "4, 1, 3, 2"
    })
    void validateCanMove_Success(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatCode(() -> sa.validateCanMove(start, end, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("한 칸을 초과하여 이동하거나 제자리 이동일 경우 예외 발생")
    void validateCanMove_Fail_InvalidPattern() {
        // given
        Position start = new Position(4, 1);
        Position moveTwoSteps = new Position(4, 3);
        Position moveLongDiagonal = new Position(6, 3);
        Position knightMove = new Position(5, 3);
        Position samePosition = new Position(4, 1);

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> sa.validateCanMove(start, moveTwoSteps, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> sa.validateCanMove(start, moveLongDiagonal, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> sa.validateCanMove(start, knightMove, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> sa.validateCanMove(start, samePosition, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다.")
        );
    }
}