package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GungTest {

    private Gung gung;
    private Board board;

    @BeforeEach
    void setUp() {
        // 한나라(HAN) 궁으로 설정
        gung = new Gung(TeamType.HAN);
        board = createBoard();
    }

    @ParameterizedTest
    @DisplayName("한나라 궁은 궁성 내에서 상하좌우 및 대각선으로 한 칸 이동할 수 있다.")
    @CsvSource({
            "5, 9, 5, 10",
            "5, 9, 5, 8",
            "5, 9, 4, 9",
            "5, 9, 6, 9",
            "5, 9, 4, 10",
            "5, 9, 6, 10",
            "5, 9, 4, 8",
            "5, 9, 6, 8"
    })
    void validateCanMove_Success(int startX, int startY, int endX, int endY) {
        // given
        Position start = createPosition(startX, startY);
        Position end = createPosition(endX, endY);

        // when & then
        assertThatCode(() -> gung.validateCanMove(start, end, board))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("한 칸을 초과하거나 제자리인 경우 예외 발생")
    @CsvSource({
            "5, 9, 5, 9",
            "5, 9, 5, 7",
            "5, 9, 3, 9",
            "5, 9, 3, 7"
    })
    void validateCanMove_Fail_InvalidDistance(int startX, int startY, int endX, int endY) {
        // given
        Position start = createPosition(startX, startY);
        Position invalidDistanceEnd = createPosition(endX, endY);

        // when & then
        assertThatThrownBy(() -> gung.validateCanMove(start, invalidDistanceEnd, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("궁이 이동할 수 없는 패턴(예: 마의 행마)인 경우 예외 발생")
    void validateCanMove_Fail_InvalidPattern() {
        // given
        Position start = createPosition(5, 9);
        Position knightEnd = createPosition(6, 7);

        // when & then
        assertThatThrownBy(() -> gung.validateCanMove(start, knightEnd, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    private Board createBoard() {
        return Board.createInitialBoard();
    }

    private Position createPosition(int x, int y) {
        return new Position(x, y);
    }
}