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

class MaTest {

    private Ma ma;
    private Board board;

    @BeforeEach
    void setUp() {
        board = Board.createInitialBoard();
        ma = new Ma(TeamType.HAN);
    }

    @ParameterizedTest
    @DisplayName("마는 이동 경로(멱)에 장애물이 없다면 L자 모양으로 이동할 수 있다.")
    @CsvSource({
            "4, 4, 5, 6",
            "4, 4, 3, 6",
            "4, 4, 5, 2",
            "4, 4, 3, 2"
    })
    void validateCanMove_Success(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatCode(() -> ma.validateCanMove(start, end, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("마의 행마법(L자)이 아니거나 제자리로 이동할 경우 예외 발생")
    void validateCanMove_Fail_InvalidPattern() {
        // given
        Position start = new Position(4, 4);
        Position straightEnd = new Position(4, 6);
        Position diagonalEnd = new Position(5, 5);
        Position longEnd = new Position(6, 7);

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> ma.validateCanMove(start, straightEnd, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> ma.validateCanMove(start, diagonalEnd, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> ma.validateCanMove(start, longEnd, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> ma.validateCanMove(start, start, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다.")
        );
    }

    @ParameterizedTest
    @DisplayName("마의 멱(직선 방향의 첫 칸)에 기물이 존재할 경우 예외 발생")
    @CsvSource({
            "4, 4, 6, 5",
            "4, 4, 6, 3",
            "4, 4, 2, 5",
            "4, 4, 2, 3"
    })
    void validateCanMove_Fail_ObstacleExist(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatThrownBy(() -> ma.validateCanMove(start, end, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
    }
}