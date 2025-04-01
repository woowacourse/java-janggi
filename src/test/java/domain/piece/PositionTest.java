package domain.piece;

import domain.spatial.Position;
import domain.spatial.Vector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PositionTest {

    @Test
    void 좌표를_생성한다() {
        assertThatCode(() -> new Position(1, 2))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 5",
            "1, 11",
            "10, 1",
            "5, 0"
    })
    void 잘못된_좌표_입력인_경우_예외가_발생한다(int row, int column) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Position(row, column))
                .withMessage("좌표 입력은 9X10 보드 이내만 가능합니다.");
    }

    @Test
    void 좌표를_이동해_반환한다() {
        // given
        Position position = new Position(5, 6);
        Vector vector = new Vector(1, 0);

        // when
        Position result = position.moveBy(vector);

        // then
        assertThat(result).isEqualTo(new Position(6, 6));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, 5, 0, 1, true",
            "1, 10, 0, 1, false"
    })
    void 좌표의_이동이_유효한지_판단한다(int row, int column, int moveRow, int moveColumn, boolean expectedResult) {
        // given
        Position position = new Position(row, column);
        Vector vector = new Vector(moveRow, moveColumn);

        // when
        boolean result = position.isMoveValid(vector);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 1, true", "4, 2, true", "4, 3, true",
            "5, 1, true", "5, 2, true", "5, 3, true",
            "6, 1, true", "6, 2, true", "6, 3, true",
            "4, 8, true", "4, 9, true", "4, 10, true",
            "5, 8, true", "5, 9, true", "5, 10, true",
            "6, 8, true", "6, 9, true", "6, 10, true",
            "3, 1, false", "3, 2, false", "3, 3, false",
            "4, 4, false", "5, 4, false", "6, 4, false",
            "7, 1, false", "7, 2, false", "7, 3, false",
            "3, 8, false", "3, 9, false", "3, 10, false",
            "4, 7, false", "5, 7, false", "6, 7, false",
            "7, 8, false", "7, 9, false", "7, 10, false",
    })
    void 궁성_좌표를_판단해_반환한다(int row, int column, boolean expected) {
        // given
        Position position = new Position(row, column);

        // when
        boolean result = position.isWithinPalace();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6, 5, 1, 1",
            "6, 4, 1, 0",
            "5, 5, 0, 1",
            "5, 4, 0, 0",
            "4, 3, -1, -1",
            "4, 4, -1, 0",
            "5, 3, 0, -1",
    })
    void 이동_방향을_반환한다(int targetRow, int targetColumn, int moveRow, int moveColumn) {
        // given
        Position start = new Position(5, 4);
        Position target = new Position(targetRow, targetColumn);

        Vector expected = new Vector(moveRow, moveColumn);

        // when
        Vector result = start.calculateVector(target);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
