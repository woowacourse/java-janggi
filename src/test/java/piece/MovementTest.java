package piece;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import board.Position;

class MovementTest {

    @CsvSource(value = {
            "1,3,2",
            "2,2,2"
    })
    @ParameterizedTest
    void 움직임의_단계를_알려주면_이동시킨_위치를_알려준다(int step, int expectedRow, int expectedColumn) {
        Movement leftLeftTop = Movement.LEFT_LEFT_TOP;
        Position position = new Position(3, 3);

        assertThat(leftLeftTop.applyMovementStep(step, position)).isEqualTo(new Position(expectedRow, expectedColumn));
    }

    @Test
    void 움직임의_단계를_넘어서면_위치를_계산할_수_없다() {
        Movement leftLeftTop = Movement.LEFT_LEFT_TOP;
        Position position = new Position(3, 3);

        assertThatThrownBy(() -> leftLeftTop.applyMovementStep(3, position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 움직임의_마지막_단계를_적용한_위치를_알려준다() {
        Movement leftLeftTop = Movement.LEFT_LEFT_TOP;
        Position position = new Position(3, 3);

        assertThat(leftLeftTop.applyMovementLastStep(position)).isEqualTo(new Position(2, 2));
    }

}
