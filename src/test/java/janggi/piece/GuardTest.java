package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class GuardTest {


    private Guard guard;

    @BeforeEach
    void setUp() {
        guard = new Guard(Team.CHO);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2, 1",
            "2, 1, 1, 1",
            "1, 1, 1, 2",
            "1, 2, 1, 1",
    })
    void 사는_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = guard.move(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(List.of(arrivalPosition)));
    }

}