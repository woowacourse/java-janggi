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

class SangTest {

    private Sang sang;
    private Board board;

    @BeforeEach
    void setUp() {
        board = Board.createInitialBoard();
        sang = new Sang(TeamType.HAN);
    }

    @ParameterizedTest
    @DisplayName("상은 경로상에 장애물이 없다면 '1칸 직선 + 2칸 대각선'으로 이동할 수 있다.")
    @CsvSource({
            "4, 4, 6, 7",
            "4, 4, 2, 7",
            "4, 5, 6, 2",
            "4, 4, 2, 1"
    })
    void validateCanMove_Success(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatCode(() -> sang.validateCanMove(start, end, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("상의 행마 패턴(대형 L자)이 아닐 경우 예외 발생")
    void validateCanMove_Fail_InvalidPattern() {
        Position start = new Position(4, 4);

        assertAll(
                () -> assertThatThrownBy(() -> sang.validateCanMove(start, new Position(4, 7), board))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> sang.validateCanMove(start, new Position(5, 6), board))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> sang.validateCanMove(start, start, board))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @DisplayName("상 이동 경로의 첫 번째 멱(직선)이나 두 번째 멱(대각선)에 기물이 있을 경우 예외 발생")
    @CsvSource({
            "4, 4, 7, 6",
            "4, 4, 1, 6",
            "4, 4, 7, 2",
            "4, 4, 1, 2"
    })
    void validateCanMove_Fail_ObstacleExist(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatThrownBy(() -> sang.validateCanMove(start, end, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
    }
}