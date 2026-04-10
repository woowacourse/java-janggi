package janggi.domain.palace;

import janggi.domain.board.Position;
import janggi.domain.piece.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {

    private Palace hanPalace;
    private Palace choPalace;

    @BeforeEach
    void setUp() {
        hanPalace = PalaceFactory.createPalace(Team.HAN);
        choPalace = PalaceFactory.createPalace(Team.CHO);
    }

    @ParameterizedTest
    @DisplayName("한 궁성 영역 내의 좌표는 포함된다.")
    @CsvSource({
            "4, 1", "5, 1", "6, 1",
            "4, 2", "5, 2", "6, 2",
            "4, 3", "5, 3", "6, 3",
    })

    void testHanPalaceIsInRange(int x, int y) {
        // when & then
        assertThat(hanPalace.isInRange(new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("한 궁성 영역 이외의 좌표는 포함되지 않는다.")
    @CsvSource({
            "3, 4", "4, 4", "5, 4", "6, 4", "7, 4",
            "3, 3",                         "7, 3",
            "3, 2",                         "7, 4",
            "3, 1",                         "7, 5"
    })
    void testHanPalaceNotIsInRange(int x, int y) {
        // when & then
        assertThat(hanPalace.isInRange(new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("초 궁성 영역 내의 좌표는 포함된다.")
    @CsvSource({
            "4, 10", "5, 10", "6, 10",
            "4, 9", "5, 9", "6, 9",
            "4, 8", "5, 8", "6, 8",
    })

    void testChoPalaceIsInRange(int x, int y) {
        // when & then
        assertThat(choPalace.isInRange(new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("초 궁성 영역 이외의 좌표는 포함되지 않는다.")
    @CsvSource({
            "3, 10",                        "7, 10",
            "3, 9",                         "7, 9",
            "3, 8",                         "7, 8",
            "3, 7", "4, 7", "5, 7", "6, 7", "7, 7"
    })
    void testChoPalaceNotIsInRange(int x, int y) {
        // when & then
        assertThat(choPalace.isInRange(new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("한 궁성의 대각선 좌표이면 true를 반환한다.")
    @CsvSource({
            "4, 1", "6, 1",
            "5, 2",
            "4, 3", "6, 3"
    })
    void testHanPalaceIsOnDiagonal(int x, int y) {
        // when & then
        assertThat(hanPalace.isOnDiagonal(new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("한 궁성의 대각선 좌표가 아니면 false를 반환한다.")
    @CsvSource({
            "5, 1", "5, 3",
            "4, 2", "6, 2"
    })
    void testHanPalaceIsNotOnDiagonal(int x, int y) {
        // when & then
        assertThat(hanPalace.isOnDiagonal(new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("두 위치가 모두 한 궁성의 대각선 좌표이면 true를 반환한다.")
    @CsvSource({
            "4, 1, 5, 2",
            "5, 2, 6, 3",
            "4, 1, 6, 3"
    })
    void testHanPalaceAreBothOnDiagonal(int x1, int y1, int x2, int y2) {
        // when & then
        assertThat(hanPalace.areBothOnDiagonal(
                new Position(x1, y1), new Position(x2, y2))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("두 위치 중 하나라도 대각선 좌표가 아니면 false를 반환한다.")
    @CsvSource({
            "4, 1, 5, 1",
            "5, 1, 6, 2",
            "4, 2, 6, 2"
    })
    void testHanPalaceAreNotBothOnDiagonal(int x1, int y1, int x2, int y2) {
        // when & then
        assertThat(hanPalace.areBothOnDiagonal(
                new Position(x1, y1), new Position(x2, y2))).isFalse();
    }
}
