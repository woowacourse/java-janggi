package position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

public class RowTest {

    @Test
    void 행을_한_칸_이동할_수_있다() {
        // given
        assertAll(
                () -> assertThat(Row.FOUR.move(1)).isEqualTo(Row.FIVE),
                () -> assertThat(Row.FOUR.move(-1)).isEqualTo(Row.THREE),
                () -> assertThat(Row.TWO.move(-1)).isEqualTo(Row.ONE),
                () -> assertThat(Row.EIGHT.move(1)).isEqualTo(Row.NINE)
        );
    }

    @Test
    void 행을_이동할_때_범위를_벗어나서_이동할_수_없다() {
        // given

        assertAll(
                () -> assertThatThrownBy(() -> Row.ONE.move(-1))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("이동할 수 없는 행입니다."),
                () -> assertThatThrownBy(() -> Row.TEN.move(1))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("이동할 수 없는 행입니다.")
        );
    }
}
