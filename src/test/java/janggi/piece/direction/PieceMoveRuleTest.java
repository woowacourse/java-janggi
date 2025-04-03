package janggi.piece.direction;

import janggi.direction.PieceMoveRule;
import janggi.direction.PieceType;
import janggi.direction.move.EdgeMoveStrategy;
import janggi.direction.obstacle.ObstacleBlockStrategy;
import janggi.piece.Piece;
import janggi.piece.board.Board;
import janggi.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceMoveRuleTest {

    @ParameterizedTest
    @CsvSource({
            "KING",
            "GUARD"
    })
    void 궁성_안에_존재하는_기물이_궁성_밖을_나가면_예외가_발생한다(final PieceType pieceType) {
        // Given
        final Position currentPosition = new Position(3, 5);
        final Piece king = new Piece(new PieceMoveRule(pieceType, new EdgeMoveStrategy(), new ObstacleBlockStrategy()));
        final Position arrivalPosition = new Position(4, 5);
        final PieceMoveRule pieceMoveRule = new PieceMoveRule(PieceType.KING, new EdgeMoveStrategy(),
                new ObstacleBlockStrategy());

        // When & Then
        Assertions.assertThatThrownBy(
                        () -> pieceMoveRule.validatePath(currentPosition, arrivalPosition,
                                new Board(Map.of(currentPosition, king))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 궁성 밖을 나갔습니다.");
    }
}
