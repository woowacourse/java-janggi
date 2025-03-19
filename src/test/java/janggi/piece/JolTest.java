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

class JolTest {

    private Jol jol;

    @BeforeEach
    void setUp() {
        jol = new Jol();
    }

    @ParameterizedTest
    @CsvSource({
            "2, 1, 1, 1",
            "1, 1, 1, 2",
            "1, 2, 1, 1",
    })
    void 졸은_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = jol.move(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(List.of(arrivalPosition)));
    }

    @Test
    void 졸은_뒤로_움직일_수_없다() {
        // Given
        final int currentY = 7;
        final int currentX = 1;
        final int arrivalY = 8;
        final int arrivalX = 1;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> jol.move(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 졸은 앞, 좌, 우로 한 칸 씩만 이동할 수 있습니다.");
    }
}