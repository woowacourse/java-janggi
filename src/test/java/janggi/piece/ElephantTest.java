package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ElephantTest {

    private Elephant elephant;

    @BeforeEach
    void setUp() {
        elephant = new Elephant(Team.CHO);
    }

    @ParameterizedTest
    @MethodSource
    void 상은_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX,
                 final List<Position> expected) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = elephant.makePath(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(expected));
    }

    private static Stream<Arguments> 상은_움직인다() {
        return Stream.of(
                Arguments.of(3, 3, 6, 5, List.of(
                        new Position(4, 3), new Position(5, 4), new Position(6, 5))
                ),
                Arguments.of(3, 3, 6, 1, List.of(
                        new Position(4, 3), new Position(5, 2), new Position(6, 1))
                ),
                Arguments.of(4, 4, 1, 6, List.of(
                        new Position(3, 4), new Position(2, 5), new Position(1, 6))
                ),
                Arguments.of(4, 4, 1, 2, List.of(
                        new Position(3, 4), new Position(2, 3), new Position(1, 2))
                ),
                Arguments.of(3, 3, 5, 6, List.of(
                        new Position(3, 4), new Position(4, 5), new Position(5, 6))
                ),
                Arguments.of(4, 4, 6, 1, List.of(
                        new Position(4, 3), new Position(5, 2), new Position(6, 1))
                ),
                Arguments.of(3, 3, 1, 6, List.of(
                        new Position(3, 4), new Position(2, 5), new Position(1, 6))
                ),
                Arguments.of(4, 4, 2, 1, List.of(
                        new Position(4, 3), new Position(3, 2), new Position(2, 1))
                )
        );
    }

    @Test
    void 상은_정해진_방식_이외의_방법으로_움직일_수_없다() {
        // Given
        final int currentY = 3;
        final int currentX = 3;
        final int arrivalY = 4;
        final int arrivalX = 5;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> elephant.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 상은 직선 1칸 이동 후 대각선 2칸으로만 이동할 수 있습니다.");
    }
}