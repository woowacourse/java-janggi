package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class RowTest {
    @Test
    void 유효한_값으로_행_객체가_생성된_경우() {
        //given
        int input = 3;

        //when
        Row row = new Row(input);

        //then
        assertThat(row.value()).isEqualTo(3);

    }


    @Test
    void 값이_최댓값을_초과하면_예외를_발행한다() {
        int input = 19;

        assertThatThrownBy(() ->  new Row(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("행의 최대 값은 8입니다.");
    }

}