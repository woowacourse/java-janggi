package janggi.util;


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
}
