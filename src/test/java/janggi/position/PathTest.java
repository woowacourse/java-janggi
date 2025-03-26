package janggi.position;

import static janggi.piece.direction.Direction.DOWN;
import static janggi.piece.direction.Direction.DOWN_RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.PieceType;
import janggi.piece.direction.Movement;
import java.util.List;
import org.junit.jupiter.api.Test;

class PathTest {

    @Test
    void 반복적으로_움직이지_않은_기물에_대해_경로를_생성한다() {
        // Given
        final Position currentPosition = new Position(1, 1);
        final Position arrivalPosition = new Position(4, 1);

        // When
        final Path path = Path.from(PieceType.CHARIOT, new Movement(DOWN, DOWN, DOWN), currentPosition,
                arrivalPosition);

        // Then
        assertThat(path.getPositions()).isEqualTo(List.of(
                new Position(2, 1),
                new Position(3, 1),
                new Position(4, 1))
        );
    }

    @Test
    void 반복적으로_움직이는_기물에_대해_경로를_생성한다() {
        // Given
        final Position currentPosition = new Position(1, 1);
        final Position arrivalPosition = new Position(4, 3);

        // When
        final Path path = Path.from(PieceType.HORSE, new Movement(DOWN, DOWN_RIGHT, DOWN_RIGHT),
                currentPosition, arrivalPosition);

        // Then
        assertThat(path.getPositions()).isEqualTo(List.of(
                new Position(2, 1),
                new Position(3, 2),
                new Position(4, 3))
        );
    }
}
