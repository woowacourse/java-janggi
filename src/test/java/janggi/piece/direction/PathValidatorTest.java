package janggi.piece.direction;

import janggi.move.Direction;
import janggi.move.Movement;
import janggi.move.ObstacleStrategy;
import janggi.move.PathValidator;
import janggi.move.Piece;
import janggi.piece.board.Board;
import janggi.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PathValidatorTest {

    @ParameterizedTest
    @CsvSource({
            "KING",
            "GUARD"
    })
    void 궁성_안에_존재하는_기물이_궁성_밖을_나가면_예외가_발생한다(final Piece piece) {
        // Given
        final Position currentPosition = new Position(3, 5);
        final Position arrivalPosition = new Position(4, 5);
        final PathValidator pathValidator = new PathValidator(Piece.KING, ObstacleStrategy.BLOCK);

        // When & Then
        Assertions.assertThatThrownBy(
                        () -> pathValidator.validatePath(currentPosition, arrivalPosition,
                                new Board(Map.of(currentPosition, piece)), new Movement(Direction.DOWN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 궁성 밖을 나갔습니다.");
    }
}
