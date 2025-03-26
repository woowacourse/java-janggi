package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.Set;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantTest {

    @ParameterizedTest
    @MethodSource
    void 상은_직선_1칸_이동_후_대각선_2칸으로_이동한다(final int currentY, final int currentX, final int arrivalY, final int arrivalX) {
        // Given
        final Position currentPosition = new Position(currentY, currentX);
        final Elephant elephant = new Elephant(Team.CHO, currentPosition);
        final Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        Assertions.assertThatCode(() ->
                elephant.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(elephant)))
        ).doesNotThrowAnyException();
    }

    private static Stream<Arguments> 상은_직선_1칸_이동_후_대각선_2칸으로_이동한다() {
        return Stream.of(
                Arguments.of(3, 3, 6, 5),
                Arguments.of(3, 3, 6, 1),
                Arguments.of(4, 4, 1, 6),
                Arguments.of(4, 4, 1, 2),
                Arguments.of(3, 3, 5, 6),
                Arguments.of(4, 4, 6, 1),
                Arguments.of(3, 3, 1, 6),
                Arguments.of(4, 4, 2, 1)
        );
    }

    @Test
    void 상은_정해진_방식_이외의_방법으로_움직일_수_없다() {
        // Given
        final int currentY = 3;
        final int currentX = 3;
        final int arrivalY = 4;
        final int arrivalX = 5;

        final Position currentPosition = new Position(currentY, currentX);
        final Elephant elephant = new Elephant(Team.CHO, currentPosition);
        final Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(
                () -> elephant.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(elephant))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
    }
}
