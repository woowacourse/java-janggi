package position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ColumnTest {

    @Test
    void 열을_한_칸_이동할_수_있다() {
        // given
        assertAll(
                () -> assertThat(Column.E.move(1)).isEqualTo(Column.F),
                () -> assertThat(Column.E.move(-1)).isEqualTo(Column.D),
                () -> assertThat(Column.B.move(-1)).isEqualTo(Column.A),
                () -> assertThat(Column.H.move(1)).isEqualTo(Column.I)
        );
    }

    @Test
    void 열을_이동할_때_범위를_벗어나서_이동할_수_없다() {
        // given

        assertAll(
                () -> assertThatThrownBy(() -> Column.A.move(-1))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("이동할 수 없는 열입니다."),
                () -> assertThatThrownBy(() -> Column.I.move(1))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("이동할 수 없는 열입니다.")
        );

    }

    @Test
    void 열을_뒤집은_좌표를_구할_수_있다() {
        Assertions.assertAll(
                () -> assertThat(Column.A.reverse()).isEqualTo(Column.I),
                () -> assertThat(Column.B.reverse()).isEqualTo(Column.H),
                () -> assertThat(Column.I.reverse()).isEqualTo(Column.A),
                () -> assertThat(Column.H.reverse()).isEqualTo(Column.B),
                () -> assertThat(Column.E.reverse()).isEqualTo(Column.E)
        );
    }
}
