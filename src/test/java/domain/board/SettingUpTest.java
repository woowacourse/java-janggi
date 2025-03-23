package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SettingUpTest {

    @DisplayName("상차림 전략을 선택한다.")
    @ParameterizedTest
    @MethodSource(value = "settingUpCase")
    void settingUpTest(String settingUp, SettingUp expected) {
        SettingUp actual = SettingUp.of(settingUp);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> settingUpCase() {
        return Stream.of(
                Arguments.of("마상마상", SettingUp.MA_SANG_MA_SANG),
                Arguments.of("마상상마", SettingUp.MA_SANG_SANG_MA),
                Arguments.of("상마마상", SettingUp.SANG_MA_MA_SANG),
                Arguments.of("상마상마", SettingUp.SANG_MA_SANG_MA)
        );
    }
}
