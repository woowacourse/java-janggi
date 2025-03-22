package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import domain.spatial.Position;
import domain.spatial.Vector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
                .withMessage("[ERROR] 좌표 입력은 9X10 보드 이내만 가능합니다.");
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
}
