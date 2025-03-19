package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class HorseTest {

    private Horse horse;

    @BeforeEach
    void setUp() {
        horse = new Horse(Team.CHO);
    }

    @ParameterizedTest
    @MethodSource
    void 마는_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX,
                 final List<Position> expected) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = horse.move(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(expected));
    }

    private static Stream<Arguments> 마는_움직인다() {
        return Stream.of(
                Arguments.of(3, 3, 5, 4, List.of(
                        new Position(4, 3), new Position(5, 4))
                ),
                Arguments.of(3, 3, 5, 2, List.of(
                        new Position(4, 3), new Position(5, 2))
                ),
                Arguments.of(3, 3, 1, 2, List.of(
                        new Position(2, 3), new Position(1, 2))
                ),
                Arguments.of(3, 3, 1, 4, List.of(
                        new Position(2, 3), new Position(1, 4))
                ),
                Arguments.of(3, 3, 4, 5, List.of(
                        new Position(3, 4), new Position(4, 5))
                ),
                Arguments.of(3, 3, 2, 5, List.of(
                        new Position(3, 4), new Position(2, 5))
                ),
                Arguments.of(3, 3, 4, 1, List.of(
                        new Position(3, 2), new Position(4, 1))
                ),
                Arguments.of(3, 3, 2, 1, List.of(
                        new Position(3, 2), new Position(2, 1))
                )
        );
    }

}