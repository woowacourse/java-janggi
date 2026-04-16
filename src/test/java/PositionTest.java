import domain.vo.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PositionTest {

    @Nested
    class FromTest {

        @ParameterizedTest
        @CsvSource({
            "0, 10",
            "1, 11"
        })
        void 목적지_좌표가_가로_1_9_세로_1_10을_벗어나면_예외가_발생한다(int x, int y) {
            // when & then
            Assertions.assertThatThrownBy(() -> Position.of(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("x 좌표는 1~9, y 좌표는 1~10, 사이여야 합니다.");
        }
    }

    @Nested
    class CamMoveTest {

        @ParameterizedTest
        @CsvSource({
            "1, 1, 0, 1",
            "5, 5, 1, 0",
            "9, 10, -1, -1"
        })
        void 이동_가능한_좌표이면_true를_반환한다(int x, int y, int dx, int dy) {
            // given
            Position position = Position.of(x, y);
            // when
            boolean result = position.canMove(dx, dy);
            // then
            Assertions.assertThat(result).isTrue();
        }

        @ParameterizedTest
        @CsvSource({
            "1, 1, -1, 0",
            "1, 1, 0, -1",
            "9, 10, 1, 0",
            "9, 10, 0, 1"
        })
        void 이동_불가능한_좌표이면_false를_반환한다(int x, int y, int dx, int dy) {
            // given
            Position position = Position.of(x, y);
            // when
            boolean result = position.canMove(dx, dy);
            // then
            Assertions.assertThat(result).isFalse();
        }
    }
}
