package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@ReplaceUnderBar
public class PositionTest {

    @Test
    void 가로_세로_2차원_좌표를_가진다() {
        Position position = new Position(1, 2);
        assertThat(position.getX()).isEqualTo(1);
        assertThat(position.getY()).isEqualTo(2);
    }

    @Test
    void 좌표를_변경할_수_있다() {
        Position position = new Position(1, 2);

        Position moved = position.moveTo(2, 4);
        assertThat(moved.getX()).isEqualTo(2);
        assertThat(moved.getY()).isEqualTo(4);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1, 1", "1,-1", "9,1", "1,10"})
    void 초기화_시_좌표를_검증한다(int x, int y) {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Position(x, y));
    }
}
