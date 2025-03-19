package janggi.piece;

import janggi.Path;
import janggi.Position;
import janggi.Team;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class ChariotTest {

    private Chariot chariot;

    @BeforeEach
    void setUp() {
        chariot = new Chariot(Team.CHO);
    }

    @ParameterizedTest
    @MethodSource
    void 차는_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX,
                 final List<Position> expected) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = chariot.move(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(expected));
    }

    private static Stream<Arguments> 차는_움직인다() {
        return Stream.of(
                Arguments.of(1, 1, 10, 1, List.of(
                        new Position(2, 1), new Position(3, 1), new Position(4, 1),
                        new Position(5, 1), new Position(6, 1), new Position(7, 1),
                        new Position(8, 1), new Position(9, 1), new Position(10, 1))
                ),

                Arguments.of(1, 2, 1, 1, List.of(new Position(1, 1))),

                Arguments.of(1, 1, 1, 9, List.of(
                        new Position(1, 2), new Position(1, 3), new Position(1, 4),
                        new Position(1, 5), new Position(1, 6), new Position(1, 7),
                        new Position(1, 8), new Position(1, 9))
                ),

                Arguments.of(2, 1, 1, 1, List.of(new Position(1, 1)))
        );
    }
}