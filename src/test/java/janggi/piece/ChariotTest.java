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

class ChariotTest {

    @ParameterizedTest
    @MethodSource
    void 차는_수직이나_수평으로_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX) {
        // Given
        final Position currentPosition = new Position(currentY, currentX);
        final Chariot chariot = new Chariot(Team.CHO, currentPosition);
        final Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        Assertions.assertThatCode(() -> {
            chariot.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(chariot)));
        }).doesNotThrowAnyException();
    }

    private static Stream<Arguments> 차는_수직이나_수평으로_움직인다() {
        return Stream.of(
                Arguments.of(1, 1, 10, 1),
                Arguments.of(1, 2, 1, 1),
                Arguments.of(1, 1, 1, 9),
                Arguments.of(2, 1, 1, 1)
        );
    }

    @Test
    void 차는_한_번에_여러_방향으로_움직일_수_없다() {
        // Given
        final int currentY = 3;
        final int currentX = 3;
        final int arrivalY = 5;
        final int arrivalX = 4;

        final Position currentPosition = new Position(currentY, currentX);
        final Chariot chariot = new Chariot(Team.CHO, currentPosition);
        final Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(
                () -> chariot.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(chariot))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
    }
}
