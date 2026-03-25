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
        assertThat(row.getValue()).isEqualTo(3);

    }


    @Test
    void 값이_최댓값을_초과하면_예외를_발행한다() {
        int input = 19;

        Row row = new Row(input);

        assertThatThrownBy(()->{
            throw new Exception("최대 값은 9입니다.");
        }).isInstanceOf(Exception.class);
    }

}