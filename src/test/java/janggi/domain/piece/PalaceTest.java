package janggi.domain.piece;

import janggi.domain.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {

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
        Palace palace = new Palace();

        boolean result = palace.isPalaceMove(Position.from(from), Position.from(to));

        assertThat(result).isEqualTo(expected);
    }
}