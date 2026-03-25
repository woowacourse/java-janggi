package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;

public class ColumnTest {
    @Test
    void 유효한_값으로_행_객체가_생성된_경우() {
        //given
        int input = 3;

        //when
        Column row = new Column(input);

        //then
        assertThat(row.getValue()).isEqualTo(3);

    }


    @Test
    void 값이_최댓값을_초과하면_예외를_발행한다() {
        int input = 19;

        assertThatThrownBy(() ->  new Column(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("열의 최대 값은 10입니다.");
    }
}
