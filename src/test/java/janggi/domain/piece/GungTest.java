/**
 * gung은 steppingPiece 테스트로 검증 가능
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

class GungTest {

    private Gung gung;

    @BeforeEach
    void setUp() {
        gung = new Gung(TeamType.HAN);
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
        assertThatCode(() -> gung.getPiecePositionsInPath(start, end))
                .doesNotThrowAnyException();
    }

//    @Test
//    @DisplayName("이동 경로에 기물이 존재하는 경우 예외 발생")
//    void validateCanMove_Fail_Piece_Exists_In_Path() {
//        List<Piece> = new ArrayList<>(new Gung(TeamType.CHU));
//    }

    /**
     * 제자리 이동은 Board로 이전
     */
//    @Test
//    @DisplayName("제자리로 이동할 경우 예외 발생")
//    void validateCanMove_Fail_Same_Position() {
//        // given
//        Position start = new Position(4, 4);
//        Position sameEnd = new Position(4, 4);
//
//        // when & then
//        assertThatThrownBy(() -> gung.validateCanMove(start, sameEnd, board))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("출발지와 목적지가 동일합니다.");
//    }

    @Test
    @DisplayName("궁이 이동할 수 없는 패턴(예: 마의 행마)인 경우 예외 발생")
    void validateCanMove_Fail_InvalidPattern() {
        // given
        Position start = createPosition(5, 9);
        Position knightEnd = createPosition(6, 7);

        // when & then
        assertThatThrownBy(() -> gung.getPiecePositionsInPath(start, knightEnd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    private Position createPosition(int x, int y) {
        return new Position(x, y);
    }
}