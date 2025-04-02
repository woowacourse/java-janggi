package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    void 졸은_전진_또는_좌우로_한칸_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = jol.makePath(currentPosition, arrivalPosition);

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
        assertThatThrownBy(() -> jol.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 졸은 앞 방향으로 이어진 선을 따라 한 칸 씩만 이동할 수 있습니다.");
    }

    @Test
    void 졸은_한_칸_초과하여_움직일_수_없다() {
        // Given
        final int currentY = 3;
        final int currentX = 3;
        final int arrivalY = 5;
        final int arrivalX = 5;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> jol.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 졸은 앞 방향으로 이어진 선을 따라 한 칸 씩만 이동할 수 있습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "9, 5, 8, 4",
            "9, 5, 8, 6",
    })
    void 졸은_궁성_안에서_간선을_따라_대각선으로_1칸_이동한다(final int currentY, final int currentX, final int arrivalY,
                                        final int arrivalX) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = jol.makePath(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(List.of(arrivalPosition)));
    }
}
