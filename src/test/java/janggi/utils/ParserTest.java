package janggi.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ParserTest {

    @Nested
    @DisplayName("정수 파싱 테스트")
    class ParseInteger {

        @Test
        @DisplayName("정상 테스트")
        void success1() {
            // given
            String input = "10";
            int expected = Integer.parseInt(input);

            // when
            int actual = Parser.parseInteger(input);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("정수가 아닌 값을 변환하는 경우 테스트")
        void failure1() {
            String input = "test";
            assertThatIllegalArgumentException()
                .isThrownBy(() -> Parser.parseInteger(input));
        }
    }
}
