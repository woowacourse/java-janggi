package janggi.domain.piece;

import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {

    @DisplayName("궁성 좌표 내에서 이동인지 판정한다.")
    @ParameterizedTest
    @CsvSource({
            "14, 25, true",
            "14, 36, true",
            "35, 45, false",
            "95, 85, true",
            "06, 84, true",
            "95, 75, false",
            "25, 95, false"
    })
    void 궁성_좌표_내에서_이동인지_판정한다(String from, String to, boolean expected) {
        // given
        Palace palace = Palace.getInstance();

        // when
        boolean result = palace.isPalaceMove(Position.from(from), Position.from(to));

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("궁성 길 이동인지 판정한다.")
    @ParameterizedTest
    @CsvSource({
            "14, 25, true",
            "14, 36, true",
            "14, 24, false",
            "15, 24, false",
            "06, 95, true",
            "06, 84, true",
            "06, 05, false",
            "05, 94, false"
    })
    void 궁성_길_이동인지_판정한다(String from, String to, boolean expected) {
        // given
        Palace palace = Palace.getInstance();

        // when
        boolean result = palace.hasRoute(Position.from(from), Position.from(to));

        // then
        assertThat(result).isEqualTo(expected);
    }
}