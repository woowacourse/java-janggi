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

class GungTest {

    private Gung gung;

    @BeforeEach
    void setUp() {
        gung = new Gung(TeamType.HAN);
    }

    @ParameterizedTest
    @DisplayName("궁은 초기 위치에서 상하좌우 및 대각선으로 한 칸 이동할 수 있다.")
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

    @Test
    @DisplayName("궁이 궁성 밖으로 이동할 경우 예외 발생")
    void validateCanMove_Fail_Out_Of_Palace() {
        // given
        Position start = createPosition(6, 9);
        Position outOfPalaceEnd = createPosition(7, 9);

        // when & then
        assertThatThrownBy(() -> gung.getPiecePositionsInPath(start, outOfPalaceEnd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @ParameterizedTest
    @DisplayName("두 칸 이동할 경우 예외 발생")
    @CsvSource({
            "5, 9, 7, 9",
            "5, 9, 5, 7",
            "5, 9, 3, 9",
            "5, 9, 7, 6",
            "5, 9, 3, 6"
    })
    void validateCanMove_Fail_Move_Two_Steps(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position twoStepsEnd = new Position(endX, endY);

        // when & then
        assertThatThrownBy(() -> gung.getPiecePositionsInPath(start, twoStepsEnd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    private Position createPosition(int x, int y) {
        return new Position(x, y);
    }
}