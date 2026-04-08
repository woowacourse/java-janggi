package domain.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Palace 클래스 테스트")
class PalaceTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    @ParameterizedTest(name = "한 궁성 안: ({0}, {1})")
    @CsvSource({
            "D, ZERO", "E, ZERO", "F, ZERO",
            "D, ONE",  "E, ONE",  "F, ONE",
            "D, TWO",  "E, TWO",  "F, TWO"
    })
    @DisplayName("HAN.contains: 한 궁성 내 9칸은 true를 반환한다")
    void hanContainsReturnsTrueForAllPalacePositions(Column column, Row row) {
        assertThat(Palace.HAN.contains(pos(column, row))).isTrue();
    }

    @ParameterizedTest(name = "한 궁성 밖: ({0}, {1})")
    @CsvSource({
            "C, ZERO",
            "G, ZERO",
            "E, THREE",
            "E, NINE",
            "A, ZERO",
    })
    @DisplayName("HAN.contains: 한 궁성 밖은 false를 반환한다")
    void hanContainsReturnsFalseOutsidePalace(Column column, Row row) {
        assertThat(Palace.HAN.contains(pos(column, row))).isFalse();
    }

    @ParameterizedTest(name = "초 궁성 안: ({0}, {1})")
    @CsvSource({
            "D, SEVEN", "E, SEVEN", "F, SEVEN",
            "D, EIGHT", "E, EIGHT", "F, EIGHT",
            "D, NINE",  "E, NINE",  "F, NINE"
    })
    @DisplayName("CHO.contains: 초 궁성 내 9칸은 true를 반환한다")
    void choContainsReturnsTrueForAllPalacePositions(Column column, Row row) {
        assertThat(Palace.CHO.contains(pos(column, row))).isTrue();
    }

    @ParameterizedTest(name = "초 궁성 밖: ({0}, {1})")
    @CsvSource({
            "C, NINE",
            "G, NINE",
            "E, SIX",
            "E, ZERO",
            "A, NINE",
    })
    @DisplayName("CHO.contains: 초 궁성 밖은 false를 반환한다")
    void choContainsReturnsFalseOutsidePalace(Column column, Row row) {
        assertThat(Palace.CHO.contains(pos(column, row))).isFalse();
    }

    @Test
    @DisplayName("Palace.of: 한 궁성 위치이면 HAN을 반환한다")
    void ofReturnsHanForHanPalacePosition() {
        assertThat(Palace.of(pos(Column.E, Row.ONE))).isEqualTo(Optional.of(Palace.HAN));
    }

    @Test
    @DisplayName("Palace.of: 초 궁성 위치이면 CHO를 반환한다")
    void ofReturnsChoForChoPalacePosition() {
        assertThat(Palace.of(pos(Column.E, Row.EIGHT))).isEqualTo(Optional.of(Palace.CHO));
    }

    @Test
    @DisplayName("Palace.of: 궁성 밖이면 empty를 반환한다")
    void ofReturnsEmptyForNonPalacePosition() {
        assertThat(Palace.of(pos(Column.E, Row.FIVE))).isEmpty();
        assertThat(Palace.of(pos(Column.A, Row.ZERO))).isEmpty();
    }

    @Test
    @DisplayName("isInAnyPalace: 한 궁성 위치이면 true를 반환한다")
    void isInAnyPalaceReturnsTrueForHanPalace() {
        assertThat(Palace.isInAnyPalace(pos(Column.D, Row.ZERO))).isTrue();
        assertThat(Palace.isInAnyPalace(pos(Column.E, Row.ONE))).isTrue();
        assertThat(Palace.isInAnyPalace(pos(Column.F, Row.TWO))).isTrue();
    }

    @Test
    @DisplayName("isInAnyPalace: 초 궁성 위치이면 true를 반환한다")
    void isInAnyPalaceReturnsTrueForChoPalace() {
        assertThat(Palace.isInAnyPalace(pos(Column.D, Row.SEVEN))).isTrue();
        assertThat(Palace.isInAnyPalace(pos(Column.E, Row.EIGHT))).isTrue();
        assertThat(Palace.isInAnyPalace(pos(Column.F, Row.NINE))).isTrue();
    }

    @Test
    @DisplayName("isInAnyPalace: 두 궁성 모두 밖이면 false를 반환한다")
    void isInAnyPalaceReturnsFalseForNonPalacePosition() {
        assertThat(Palace.isInAnyPalace(pos(Column.E, Row.FOUR))).isFalse();
        assertThat(Palace.isInAnyPalace(pos(Column.E, Row.FIVE))).isFalse();
        assertThat(Palace.isInAnyPalace(pos(Column.C, Row.ONE))).isFalse();
        assertThat(Palace.isInAnyPalace(pos(Column.G, Row.EIGHT))).isFalse();
    }

    @Test
    @DisplayName("경계값: 한 궁성 코너 4개를 정확히 포함한다")
    void hanPalaceCornersAreContained() {
        assertThat(Palace.HAN.contains(pos(Column.D, Row.ZERO))).isTrue();
        assertThat(Palace.HAN.contains(pos(Column.F, Row.ZERO))).isTrue();
        assertThat(Palace.HAN.contains(pos(Column.D, Row.TWO))).isTrue();
        assertThat(Palace.HAN.contains(pos(Column.F, Row.TWO))).isTrue();
    }

    @Test
    @DisplayName("경계값: 초 궁성 코너 4개를 정확히 포함한다")
    void choPalaceCornersAreContained() {
        assertThat(Palace.CHO.contains(pos(Column.D, Row.SEVEN))).isTrue();
        assertThat(Palace.CHO.contains(pos(Column.F, Row.SEVEN))).isTrue();
        assertThat(Palace.CHO.contains(pos(Column.D, Row.NINE))).isTrue();
        assertThat(Palace.CHO.contains(pos(Column.F, Row.NINE))).isTrue();
    }

    @Test
    @DisplayName("경계값: 한 궁성과 초 궁성이 서로 상대 궁성을 포함하지 않는다")
    void hanAndChoDoNotContainEachOthersPalace() {
        Position hanCenter = pos(Column.E, Row.ONE);
        Position choCenter = pos(Column.E, Row.EIGHT);

        assertThat(Palace.HAN.contains(choCenter)).isFalse();
        assertThat(Palace.CHO.contains(hanCenter)).isFalse();
    }

    @Test
    @DisplayName("validateContains: 궁성 안 위치이면 예외를 던지지 않는다")
    void validateContainsDoesNotThrowForPalacePosition() {
        Assertions.assertDoesNotThrow(
                () -> Palace.HAN.validateContains(pos(Column.E, Row.ONE)));
        Assertions.assertDoesNotThrow(
                () -> Palace.CHO.validateContains(pos(Column.E, Row.EIGHT)));
    }

    @ParameterizedTest(name = "한 궁성 밖 ({0}, {1}) → 예외")
    @CsvSource({"C, ZERO", "G, TWO", "E, THREE", "E, NINE"})
    @DisplayName("validateContains: 한 궁성 밖 위치이면 예외를 던진다")
    void validateContainsThrowsForOutsideHanPalace(Column column, Row row) {
        assertThatThrownBy(() -> Palace.HAN.validateContains(pos(column, row)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("궁성 밖");
    }

    @ParameterizedTest(name = "초 궁성 밖 ({0}, {1}) → 예외")
    @CsvSource({"C, NINE", "G, SEVEN", "E, SIX", "E, ZERO"})
    @DisplayName("validateContains: 초 궁성 밖 위치이면 예외를 던진다")
    void validateContainsThrowsForOutsideChoPalace(Column column, Row row) {
        assertThatThrownBy(() -> Palace.CHO.validateContains(pos(column, row)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("궁성 밖");
    }

    @ParameterizedTest(name = "대각선 위 ({0}, {1}) → 예외 없음")
    @CsvSource({"D, ZERO", "F, ZERO", "E, ONE", "D, TWO", "F, TWO"})
    @DisplayName("validateOnDiagonal: 한 궁성 대각선 위 위치이면 예외를 던지지 않는다")
    void validateOnDiagonalDoesNotThrowForDiagonalPosition(Column column, Row row) {
        Assertions.assertDoesNotThrow(
                () -> Palace.HAN.validateOnDiagonal(pos(column, row)));
    }

    @ParameterizedTest(name = "대각선 아님 ({0}, {1}) → 예외")
    @CsvSource({"E, ZERO", "D, ONE", "F, ONE", "E, TWO"})
    @DisplayName("validateOnDiagonal: 한 궁성 대각선 아닌 위치이면 예외를 던진다")
    void validateOnDiagonalThrowsForNonDiagonalPosition(Column column, Row row) {
        assertThatThrownBy(() -> Palace.HAN.validateOnDiagonal(pos(column, row)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
                .hasMessageContaining("대각선");
    }

    @Test
    @DisplayName("validateOnDiagonal: 궁성 밖 위치이면 예외를 던진다")
    void validateOnDiagonalThrowsForOutsidePalace() {
        assertThatThrownBy(() -> Palace.HAN.validateOnDiagonal(pos(Column.A, Row.ZERO)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}
