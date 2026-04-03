/**
 * 졸은 steppingPiece에서 테스트 가능
 * 다만 이동 경로에 대해서는 검증 필요할지도
 * 기물마다 다른 것은 각각 테스트해줘야 할듯
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

class JolTest {

    private Jol chuJol;
    private Jol hanJol;

    @BeforeEach
    void setUp() {
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
        assertThatCode(() -> chuJol.getPiecePositionsInPath(start, end))
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
        assertThatCode(() -> hanJol.getPiecePositionsInPath(start, end))
                .doesNotThrowAnyException();
    }

    /**
     * 제자리는 Board에서
     */
//    @Test
//    @DisplayName("제자리로 이동할 경우 예외 발생")
//    void validateCanMove_Fail_Same_Position() {
//        // given
//        Position start = new Position(4, 4);
//        Position sameEnd = new Position(4, 4);
//
//        // when & then
//        assertThatThrownBy(() -> chuJol.validateCanMove(start, sameEnd, board))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("출발지와 목적지가 동일합니다.");
//    }

    @Test
    @DisplayName("졸이 이동할 수 없는 위치일 경우 예외 발생")
    void validateCanMove_Fail_Invalid_Position() {
        // given
        Position chuJolStart = new Position(4, 4);
        Position chuJolInvalidEnd = new Position(4, 3);
        Position hanJolStart = new Position(4, 4);
        Position hanJolInvalidEnd = new Position(4, 5);
        Position diagonalEnd = new Position(5, 5);

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> chuJol.getPiecePositionsInPath(chuJolStart, chuJolInvalidEnd))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> hanJol.getPiecePositionsInPath(hanJolStart, hanJolInvalidEnd))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> chuJol.getPiecePositionsInPath(chuJolStart, diagonalEnd))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다.")
        );
    }
}