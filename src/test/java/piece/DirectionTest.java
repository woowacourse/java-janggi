package piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DirectionTest {

    @Test
    void 직진_방향을_반환한다() {
        assertThat(Direction.getStraightDirection()).contains(
                Direction.LEFT,
                Direction.RIGHT,
                Direction.TOP,
                Direction.BOTTOM
        );
    }

    @CsvSource(value = {
            "LEFT_TOP,true",
            "LEFT_BOTTOM,true",
            "RIGHT_TOP,true",
            "RIGHT_BOTTOM,true",
            "LEFT,false",
            "RIGHT,false",
            "TOP,false",
            "BOTTOM,false"
    })
    @ParameterizedTest
    void 대각선_방향인지_알려준다(Direction direction, boolean expected) {
        assertThat(direction.isDiagonal()).isEqualTo(expected);
    }

}
