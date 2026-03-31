package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ColumnTest {

    @Nested
    class 생성 {
        @Test
        void 유효한_값으로_열_객체가_생성된_경우() {
            int input = 3;

            Column column = new Column(input);

            assertThat(column.value()).isEqualTo(3);
        }

        @Test
        void 보드_밖_값으로도_열_객체를_만들_수_있다() {
            Column column = new Column(19);

            assertThat(column.value()).isEqualTo(19);
            assertThat(column.isInsideBoard()).isFalse();
        }
    }

    @Nested
    class 보드_안쪽_판정 {
        @Test
        void 범위_안이면_true() {
            assertThat(new Column(0).isInsideBoard()).isTrue();
            assertThat(new Column(8).isInsideBoard()).isTrue();
            assertThat(new Column(4).isInsideBoard()).isTrue();
        }

        @Test
        void 범위_밖이면_false() {
            assertThat(new Column(-1).isInsideBoard()).isFalse();
            assertThat(new Column(9).isInsideBoard()).isFalse();
        }
    }
}
