/**
 * SteppingPiece에서 테스트
 */
package janggi.domain.piece;

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

class SaTest {

    private Sa sa;

    @BeforeEach
    void setUp() {
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
        assertThatCode(() -> sa.getPiecePositionsInPath(start, end))
                .doesNotThrowAnyException();
    }

    /**
     * 제자리 테스트는 Board에서
     */
//    @Test
//    @DisplayName("제자리로 이동할 경우 예외 발생")
//    void validateCanMove_Fail_Same_Position() {
//        // given
//        Position start = new Position(4, 4);
//        Position sameEnd = new Position(4, 4);
//
//        // when & then
//        assertThatThrownBy(() -> sa.validateCanMove(start, sameEnd, board))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("출발지와 목적지가 동일합니다.");
//    }

    @Test
    @DisplayName("사가 이동할 수 없는 위치일 경우 예외 발생")
    void validateCanMove_Fail_Invalid_Position() {
        // given
        Position start = new Position(4, 1);
        Position moveTwoSteps = new Position(4, 3);
        Position moveLongDiagonal = new Position(6, 3);
        Position knightMove = new Position(5, 3);

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> sa.getPiecePositionsInPath(start, moveTwoSteps))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> sa.getPiecePositionsInPath(start, moveLongDiagonal))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> sa.getPiecePositionsInPath(start, knightMove))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다.")
        );
    }
}