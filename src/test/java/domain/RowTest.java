package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RowTest {

    @Nested
    class 생성 {
        @Test
        void 유효한_값으로_행_객체가_생성된_경우() {
            int input = 3;

            Row row = new Row(input);

            assertThat(row.value()).isEqualTo(3);
        }

        @Test
        void 보드_밖_값으로도_행_객체를_만들_수_있다() {
            Row row = new Row(19);

            assertThat(row.value()).isEqualTo(19);
            assertThat(row.isInsideBoard()).isFalse();
        }
    }

    @Nested
    class 보드_안쪽_판정 {
        @Test
        void 범위_안이면_true() {
            assertThat(new Row(0).isInsideBoard()).isTrue();
            assertThat(new Row(9).isInsideBoard()).isTrue();
            assertThat(new Row(5).isInsideBoard()).isTrue();
        }

        @Test
        void 범위_밖이면_false() {
            assertThat(new Row(-1).isInsideBoard()).isFalse();
            assertThat(new Row(10).isInsideBoard()).isFalse();
        }
    }
}
