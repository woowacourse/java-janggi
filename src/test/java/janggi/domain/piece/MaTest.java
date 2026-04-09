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
}