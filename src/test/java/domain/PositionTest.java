package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @Test
    void 위치는_행과_열의_정보가_있어야한다() {
        Position position = new Position(0, 0);

        assertThat(position.getX()).isEqualTo(0);
        assertThat(position.getY()).isEqualTo(0);
    }

    @CsvSource(value = {"0,0", "0,8", "9,0", "9,8"})
    @ParameterizedTest
    void 위치는_장기판_내부에_존재해야_한다(int x, int y) {
        assertDoesNotThrow(() -> new Position(x, y));
    }

    @CsvSource(value = {"-1, 0", "0, -1", "10, 0", "0, 9"})
    @ParameterizedTest
    void 장기판_내부에_존재하지_않으면_위치를_생성할_수_없다(int x, int y) {
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
