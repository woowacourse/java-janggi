package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KingTest {

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
        final King king = new King(Team.CHO, currentPosition);
        final Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        Assertions.assertThatCode(() ->
                king.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(king)))
        ).doesNotThrowAnyException();
    }

    @Test
    void 왕은_한_칸_초과하여_움직일_수_없다() {
        // Given
        final int currentY = 3;
        final int currentX = 3;
        final int arrivalY = 5;
        final int arrivalX = 5;

        final Position currentPosition = new Position(currentY, currentX);
        final King king = new King(Team.CHO, currentPosition);
        final Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(
                () -> king.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(king))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
    }
}
