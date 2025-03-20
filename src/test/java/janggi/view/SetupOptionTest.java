package janggi.view;

import static janggi.view.SetupOption.INNER_SETUP;
import static janggi.view.SetupOption.LEFT_SETUP;
import static janggi.view.SetupOption.OUTER_SETUP;
import static janggi.view.SetupOption.RIGHT_SETUP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SetupOptionTest {

    @DisplayName("상차림 번호에 따라 맞는 enum을 반환한다")
    @ParameterizedTest
    @MethodSource
    void testSetupOption(String input, SetupOption expected) {
        // given
        // when
        SetupOption setupOption = SetupOption.of(input);
        // then
        assertThat(setupOption).isEqualTo(expected);
    }

    private static Stream<Arguments> testSetupOption() {
        return Stream.of(
                Arguments.of("1", INNER_SETUP),
                Arguments.of("2", OUTER_SETUP),
                Arguments.of("3", RIGHT_SETUP),
                Arguments.of("4", LEFT_SETUP)
        );
    }

    @DisplayName("잘못된 번호를 입력하면 예외가 발생한다.")
    @Test
    void testSetupOptionException() {
        assertThatThrownBy(() -> SetupOption.of("5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 1~4의 숫자만 입력할 수 있습니다.");
    }
}
