package janggi.piece;

import janggi.Path;
import janggi.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ByeongTest {

    private Byeong byeong;

    @BeforeEach
    void setUp() {
        byeong = new Byeong();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2, 1",
            "1, 1, 1, 2",
            "1, 2, 1, 1",
    })
    void 병은_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = byeong.move(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(List.of(arrivalPosition)));
    }

    @Test
    void 병은_뒤로_움직일_수_없다() {
        // Given
        final int currentY = 2;
        final int currentX = 1;
        final int arrivalY = 1;
        final int arrivalX = 1;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> byeong.move(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 병은 앞, 좌, 우로 한 칸 씩만 이동할 수 있습니다.");
    }
}
