package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import domain.direction.Vector;
import domain.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @Test
    void 좌표를_생성한다() {
        assertThatCode(() -> Position.of(1, 2))
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
                .isThrownBy(() -> Position.of(row, column))
                .withMessage("[ERROR] 좌표 입력은 9X10 보드 이내만 가능합니다.");
    }

    @Test
    void 좌표를_병합해_반환한다() {
        // given
        Position position = Position.of(5, 6);
        Vector vector = Vector.UP_LEFT;

        // when
        Position result = position.merge(vector);

        // then
        assertThat(result).isEqualTo(Position.of(4, 5));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,10,true",
            "10,9,false"
    })
    void 좌표가_유효한지_판단한다(int row, int column, boolean expectedResult) {
        // given
        Position position = Position.ofUnverified(row, column);

        // when
        boolean result = position.isValid();

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void 좌표를_좌우_대칭_시킨다() {
        // given
        Position position = Position.of(2, 6);

        // when
        Position result = position.flipLeftRight();

        // then
        assertThat(result).isEqualTo(Position.of(8, 6));
    }

    @Test
    void 좌표를_상하_대칭_시킨다() {
        // given
        Position position = Position.of(2, 7);

        // when
        Position result = position.flipUpDown();

        // then
        assertThat(result).isEqualTo(Position.of(2, 4));
    }

    @Test
    void 좌표의_열을_움직인다() {
        // given
        Position position = Position.of(1, 5);

        // when
        Position result = position.moveRow(4);

        // then
        assertThat(result).isEqualTo(Position.of(5, 5));
    }
}
