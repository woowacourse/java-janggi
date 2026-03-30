package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ElephantSettingTest {

    @ParameterizedTest
    @CsvSource(value = {
            "1, LEFT_ELEPHANT",
            "2, RIGHT_ELEPHANT",
            "3, INNER_ELEPHANT",
            "4, OUTER_ELEPHANT"
    })
    void 명령어와_매칭되는_상차림을_반환한다(String command, ElephantSetting expectedElephantSetting) {
        // when
        ElephantSetting result = ElephantSetting.findElephantSettingBy(command);
        // then
        assertThat(result).isEqualTo(expectedElephantSetting);
    }
}
