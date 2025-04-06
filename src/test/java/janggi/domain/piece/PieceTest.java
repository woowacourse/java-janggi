package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.piece.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceTest {

    @ParameterizedTest
    @CsvSource({
            "KING",
            "GUARD"
    })
    void 궁성_안에_존재하는_기물이_궁성_밖을_나가면_예외가_발생한다(final Piece piece) {
        // Given
        final Position currentPosition = new Position(3, 5);
        final Position arrivalPosition = new Position(4, 5);

        // When & Then
        Assertions.assertThatThrownBy(
                        () -> piece.validateMovement(currentPosition, arrivalPosition,
                                new Board(Map.of(currentPosition, piece))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 궁성 밖을 나갔습니다.");
    }
}
