package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @Test
    void 위치는_행과_열의_정보가_있어야한다() {
        Position position = new Position(1, 1);

        assertThat(position.getColumn()).isEqualTo(1);
        assertThat(position.getRow()).isEqualTo(1);
    }

    @CsvSource(value = {"1,1", "1,10", "9,1", "9,10"})
    @ParameterizedTest
    void 위치는_장기판_내부에_존재해야_한다(int x, int y) {
        assertDoesNotThrow(() -> new Position(x, y));
    }

//    @CsvSource(value = {"0,1", "1,0", "10,1", "1,11"})
//    @ParameterizedTest
//    void 장기판_내부에_존재하지_않으면_위치를_생성할_수_없다(int x, int y) {
//        assertThatThrownBy(() -> new Position(x, y))
//                .isInstanceOf(IllegalArgumentException.class);
//    }

}
