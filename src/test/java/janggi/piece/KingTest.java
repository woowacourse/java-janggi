package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class KingTest {

    private King king;

    @BeforeEach
    void setUp() {
        king = new King(Team.CHO);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2, 1",
            "2, 1, 1, 1",
            "1, 1, 1, 2",
            "1, 2, 1, 1",
    })
    void 왕은_한칸씩_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX) {
        // Given
        final Position currentPosition = new Position(currentY, currentX);
        final Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        final Path path = king.makePath(currentPosition, arrivalPosition, Map.of(currentPosition, new King(Team.CHO)));

        // Then
        assertThat(path).isEqualTo(new Path(List.of(arrivalPosition)));
    }

    @Test
    void 왕은_한_칸_초과하여_움직일_수_없다() {
        // Given
        final int currentY = 3;
        final int currentX = 3;
        final int arrivalY = 5;
        final int arrivalX = 5;

        final Position currentPosition = new Position(currentY, currentX);
        final Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> king.makePath(currentPosition, arrivalPosition, Map.of(currentPosition, new King(Team.CHO))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
    }
}
