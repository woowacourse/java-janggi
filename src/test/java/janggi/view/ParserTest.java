package janggi.view;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void 구분자를_기준으로_문자열을_분리한다() {
        // given
        String expression = "10,20";
        String delimiter = ",";
        // when
        List<Integer> parsedExpression = Parser.parseByDelimiter(delimiter, expression);
        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(parsedExpression).hasSize(2);
            assertSoftly.assertThat(parsedExpression.getFirst()).isEqualTo(10);
            assertSoftly.assertThat(parsedExpression.getLast()).isEqualTo(20);
        });
    }

    @Test
    void 숫자가_아닌_문자가_포함되면_예외가_발생한다() {
        assertThatThrownBy(() -> Parser.parseByDelimiter(",", "10,a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 숫자만 입력 가능합니다");
    }
}
