/**
 * 마는 LeapingPiece에서 테스트해줘야
 * 근데 각 기물 이동 규칙은 여기서 테스트해야 함
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

class MaTest {

    private Ma ma;

    @BeforeEach
    void setUp() {
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
        assertThatCode(() -> ma.getPiecePositionsInPath(start, end))
                .doesNotThrowAnyException();
    }

    /**
     * Board에서 진행
     */
//    @Test
//    @DisplayName("제자리로 이동할 경우 예외 발생")
//    void validateCanMove_Fail_Same_Position() {
//        // given
//        Position start = new Position(4, 4);
//        Position sameEnd = new Position(4, 4);
//
//        // when & then
//        assertThatThrownBy(() -> ma.validateCanMove(start, sameEnd, board))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("출발지와 목적지가 동일합니다.");
//    }

    @Test
    @DisplayName("마가 이동할 수 없는 위치일 경우 예외 발생")
    void validateCanMove_Fail_Invalid_Position() {
        // given
        Position start = new Position(4, 4);
        Position diagonalEnd = new Position(5, 5);

        // when & then
        assertThatThrownBy(() -> ma.getPiecePositionsInPath(start, diagonalEnd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    // LeapingPiece에서 테스트
//    @ParameterizedTest
//    @DisplayName("이동 경로 중간에 기물이 존재할 경우 예외 발생")
//    @CsvSource({
//            "4, 4, 6, 5",
//            "4, 4, 6, 3",
//            "4, 4, 2, 5",
//            "4, 4, 2, 3"
//    })
//    void validateCanMove_Fail_ObstacleExist(int startX, int startY, int endX, int endY) {
//        // given
//        Position start = new Position(startX, startY);
//        Position end = new Position(endX, endY);
//
//        // when & then
//        assertThatThrownBy(() -> ma.getPiecePositionsInPath(start, end))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
//    }
}