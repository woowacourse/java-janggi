package janggi.domain.piece.direction;

import janggi.domain.piece.ObstacleTraversalRule;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Movement;
import janggi.domain.piece.position.PathValidator;
import janggi.domain.piece.Piece;
import janggi.domain.piece.position.Position;
import janggi.domain.board.Board;
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
        final PathValidator pathValidator = new PathValidator(ObstacleTraversalRule.BLOCK);

        // When & Then
        Assertions.assertThatThrownBy(
                        () -> pathValidator.validatePath(currentPosition, arrivalPosition, true,
                                new Board(Map.of(currentPosition, piece)), new Movement(Direction.DOWN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 궁성 밖을 나갔습니다.");
    }
}
