package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.team.TeamType;
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

    @ParameterizedTest
    @DisplayName("초나라 졸은 궁성 내부에서 대각선 위로 이동할 수 있다.")
    @CsvSource({
            "4, 8, 5, 9",
            "6, 8, 5, 9",
            "5, 9, 4, 10",
            "5, 9, 6, 10",
    })
    void validateCanMove_ChuJol_In_Palace_Diagonal_Success_Move_Forward(int startX, int startY, int endX, int endY) {
        // given
        Position chuJolStart = new Position(startX, startY);
        Position chuJolEnd = new Position(endX, endY);

        // when & then
        assertThatCode(() -> chuJol.getPiecePositionsInPath(chuJolStart, chuJolEnd))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("한나라 졸은 궁성 내부에서 대각선 위로 이동할 수 있다.")
    @CsvSource({
            "4, 3, 5, 2",
            "6, 3, 5, 2",
            "5, 2, 4, 1",
            "5, 2, 6, 1",
    })
    void validateCanMove_HanJol_In_Palace_Diagonal_Success_Move_Forward(int startX, int startY, int endX, int endY) {
        // given
        Position hanJolStart = new Position(startX, startY);
        Position hanJolEnd = new Position(endX, endY);

        // when & then
        assertThatCode(() -> hanJol.getPiecePositionsInPath(hanJolStart, hanJolEnd))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("초나라 졸이 궁성 내부에서 대각선 아래로 이동할 경우 예외 발생")
    @CsvSource({
            "4, 10, 5, 9",
            "6, 10, 5, 9",
            "5, 9, 4, 8",
            "5, 9, 6, 8",
    })
    void validateCanMove_ChuJol_In_Palace_Diagonal_Fail_Move_Back(int startX, int startY, int endX, int endY) {
        // given
        Position chuJolStart = new Position(startX, startY);
        Position chuJolEnd = new Position(endX, endY);

        // when & then
        assertThatThrownBy(() -> chuJol.getPiecePositionsInPath(chuJolStart, chuJolEnd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @ParameterizedTest
    @DisplayName("한나라 졸이 궁성 내부에서 대각선 위로 이동할 경우 예외 발생")
    @CsvSource({
            "4, 1, 5, 2",
            "6, 1, 5, 2",
            "5, 2, 4, 3",
            "5, 2, 6, 3",
    })
    void validateCanMove_HanJol_In_Palace_Diagonal_Fail_Move_Back(int startX, int startY, int endX, int endY) {
        // given
        Position hanJolStart = new Position(startX, startY);
        Position hanJolEnd = new Position(endX, endY);

        // when & then
        assertThatThrownBy(() -> hanJol.getPiecePositionsInPath(hanJolStart, hanJolEnd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }
}