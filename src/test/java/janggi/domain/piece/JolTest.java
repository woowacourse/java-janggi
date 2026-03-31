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

class JolTest {

    private Board board;
    private Jol chuJol;
    private Jol hanJol;

    @BeforeEach
    void setUp() {
        board = Board.createInitialBoard();
        chuJol = new Jol(TeamType.CHU);
        hanJol = new Jol(TeamType.HAN);
    }

    @ParameterizedTest
    @DisplayName("초나라 졸은 위, 왼쪽, 오른쪽으로 한 칸씩 이동할 수 있다.")
    @CsvSource({
            "1, 4, 1, 5",
            "3, 4, 2, 4",
            "3, 4, 4, 4"
    })
    void validateCanMove_ChuJol_Success(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatCode(() -> chuJol.validateCanMove(start, end, board))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("한나라 졸은 아래, 왼쪽, 오른쪽으로 한 칸씩 이동할 수 있다.")
    @CsvSource({
            "1, 7, 1, 6",
            "3, 7, 2, 7",
            "3, 7, 4, 7"
    })
    void validateCanMove_HanJol_Success(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatCode(() -> hanJol.validateCanMove(start, end, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸이 후진하거나, 대각선으로 움직이거나, 두 칸 이상 이동할 경우 예외 발생")
    void validateCanMove_Fail_InvalidPattern() {
        // given
        Position chuStart = new Position(1, 4);
        Position hanStart = new Position(1, 7);

        assertAll(
                () -> assertThatThrownBy(() -> chuJol.validateCanMove(chuStart, new Position(1, 3), board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> hanJol.validateCanMove(hanStart, new Position(1, 8), board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> chuJol.validateCanMove(chuStart, new Position(2, 5), board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> chuJol.validateCanMove(chuStart, new Position(1, 6), board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> chuJol.validateCanMove(chuStart, chuStart, board))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다.")
        );
    }
}