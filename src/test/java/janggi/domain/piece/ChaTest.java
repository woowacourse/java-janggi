package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChaTest {

    private Cha cha;
    private Board board;

    @BeforeEach
    void setUp() {
        board = Board.createInitialBoard();
        cha = new Cha(TeamType.HAN);
    }

    @ParameterizedTest
    @DisplayName("차는 이동 경로에 장애물이 없다면 상하좌우 직선으로 이동할 수 있다.")
    @CsvSource({
            "4, 5, 4, 9",
            "4, 5, 4, 2",
            "4, 5, 1, 5",
            "4, 5, 9, 5"
    })
    void validateCanMove_Success(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatCode(() -> cha.validateCanMove(start, end, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("직선 경로가 아니거나 제자리로 이동할 경우 예외 발생")
    void validateCanMove_Fail_InvalidPattern() {
        // given
        Position start = new Position(4, 4);
        Position diagonalEnd = new Position(5, 5);
        Position knightEnd = new Position(5, 6);
        Position sameEnd = new Position(4, 4);

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> cha.validateCanMove(start, diagonalEnd, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> cha.validateCanMove(start, knightEnd, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> cha.validateCanMove(start, sameEnd, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다.")
        );
    }

    @ParameterizedTest
    @DisplayName("이동 경로 중간에 기물이 존재할 경우 예외 발생")
    @CsvSource({
            "4, 4, 1, 4",
            "4, 4, 7, 4"
    })
    void validateCanMove_Fail_ObstacleExist(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatThrownBy(() -> cha.validateCanMove(start, end, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
    }
}