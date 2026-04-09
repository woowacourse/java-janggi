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

class SaTest {

    private Sa sa;

    @BeforeEach
    void setUp() {
        sa = new Sa(TeamType.HAN);
    }

    @ParameterizedTest
    @DisplayName("초기 위치에서 상, 우, 오른쪽 대각선 위으로 한 칸 이동할 수 있다.")
    @CsvSource({
            "4, 1, 4, 2",
            "4, 1, 5, 1",
            "4, 1, 5, 2",
    })
    void validateCanMove_Success(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatCode(() -> sa.getPiecePositionsInPath(start, end))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("초기 위치에서 궁성 밖으로 이동할 경우 예외 발생")
    @CsvSource({
            "4, 1, 3, 1",
            "4, 1, 3, 2"
    })
    void validateCanMove_Fail_Out_Of_Palace(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position outOfPalaceEnd = new Position(endX, endY);

        // when & then
        assertThatThrownBy(() -> sa.getPiecePositionsInPath(start, outOfPalaceEnd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @ParameterizedTest
    @DisplayName("두 칸 이동할 경우 예외 발생")
    @CsvSource({
            "4, 1, 4, 3",
            "4, 1, 6, 3",
            "4, 1, 6, 1"
    })
    void validateCanMove_Fail_Move_Two_Steps(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position twoStepsEnd = new Position(endX, endY);

        // when & then
        assertThatThrownBy(() -> sa.getPiecePositionsInPath(start, twoStepsEnd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("사가 이동할 수 없는 위치일 경우 예외 발생")
    void validateCanMove_Fail_Invalid_Position() {
        // given
        Position start = new Position(4, 1);
        Position knightMove = new Position(5, 3);

        // when & then
        assertThatThrownBy(() -> sa.getPiecePositionsInPath(start, knightMove))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }
}