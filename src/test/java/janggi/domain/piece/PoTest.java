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

class PoTest {

    private Po po;
    private Board board;

    @BeforeEach
    void setUp() {
        board = Board.createInitialBoard();
        po = new Po(TeamType.HAN);
    }

    @ParameterizedTest
    @DisplayName("포는 경로 사이에 기물이 정확히 하나(포 제외) 있을 때만 넘어갈 수 있다.")
    @CsvSource({
            "3, 8, 3, 6",
            "1, 8, 1, 6",
            "7, 8, 7, 6"
    })
    void validateCanMove_Success(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatCode(() -> po.validateCanMove(start, end, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("직선 경로가 아니거나 제자리 이동일 경우 예외 발생")
    void validateCanMove_Fail_InvalidPattern() {
        Position start = new Position(4, 4);

        assertAll(
                () -> assertThatThrownBy(() -> po.validateCanMove(start, new Position(5, 5), board))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> po.validateCanMove(start, start, board))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("이동 경로에 기물이 하나도 없을 경우 예외 발생")
    void validateCanMove_Fail_NoObstacle() {
        Position start = new Position(4, 4);
        Position end = new Position(4, 6);

        // when & then
        assertThatThrownBy(() -> po.validateCanMove(start, end, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 존재하지 않아 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("이동 경로에 기물이 2개 이상일 경우 예외 발생")
    void validateCanMove_Fail_TooManyObstacles() {
        Position start = new Position(2, 10);
        Position end = new Position(2, 1);

        // when & then
        assertThatThrownBy(() -> po.validateCanMove(start, end, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 1개 이상 존재합니다.");
    }

    @Test
    @DisplayName("포의 이동 경로에 포가 존재할 경우 예외 발생")
    void validateCanMove_Fail_PoAsObstacle() {
        Position start = new Position(2, 9);
        Position end = new Position(2, 7);

        // when & then
        assertThatThrownBy(() -> po.validateCanMove(start, end, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 넘을 수 없습니다.");
    }
}