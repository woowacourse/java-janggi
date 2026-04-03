/**
 * LeapingPiece에서 테스트
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

class SangTest {

    private Sang sang;

    @BeforeEach
    void setUp() {
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
        assertThatCode(() -> sang.getPiecePositionsInPath(start, end))
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
//        assertThatThrownBy(() -> sang.validateCanMove(start, sameEnd, board))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("출발지와 목적지가 동일합니다.");
//    }

    @Test
    @DisplayName("상이 이동할 수 없는 위치일 경우 예외 발생")
    void validateCanMove_Fail_Invalid_Position() {
        Position start = new Position(4, 4);

        assertAll(
                () -> assertThatThrownBy(() -> sang.getPiecePositionsInPath(start, new Position(4, 7)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> sang.getPiecePositionsInPath(start, new Position(5, 6)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다.")
        );
    }
/**
 * LeapingPiece에서 테스트
 */
//    @ParameterizedTest
//    @DisplayName("이동 경로 중간에 기물이 존재할 경우 예외 발생")
//    @CsvSource({
//            "4, 4, 7, 6",
//            "4, 4, 1, 6",
//            "4, 4, 7, 2",
//            "4, 4, 1, 2"
//    })
//    void validateCanMove_Fail_ObstacleExist(int startX, int startY, int endX, int endY) {
//        // given
//        Position start = new Position(startX, startY);
//        Position end = new Position(endX, endY);
//
//        // when & then
//        assertThatThrownBy(() -> sang.validateCanMove(start, end, board))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
//    }
}