package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseTest {

    private Horse horse;

    @BeforeEach
    void setUp() {
        horse = new Horse(Team.CHO);
    }

    @ParameterizedTest
    @MethodSource
    void 마는_직선_1칸_이동_후_대각선_1칸으로_이동한다(final int currentY, final int currentX, final int arrivalY, final int arrivalX,
                                     final List<Position> expected) {
        // Given
        final Position currentPosition = new Position(currentY, currentX);
        final Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        final Path path = horse.makePath(currentPosition, arrivalPosition, Map.of(currentPosition, new Horse(Team.CHO)));

        // Then
        assertThat(path).isEqualTo(new Path(expected));
    }

    private static Stream<Arguments> 마는_직선_1칸_이동_후_대각선_1칸으로_이동한다() {
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

    @Test
    void 말은_정해진_방식_이외의_방법으로_움직일_수_없다() {
        // Given
        final int currentY = 3;
        final int currentX = 3;
        final int arrivalY = 4;
        final int arrivalX = 6;

        final Position currentPosition = new Position(currentY, currentX);
        final Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(
                () -> horse.makePath(currentPosition, arrivalPosition, Map.of(currentPosition, new Horse(Team.CHO))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
    }
}
