package janggi.piece.direction;

import janggi.direction.PieceMoveRule;
import janggi.direction.PieceType;
import janggi.direction.obstacle.ObstacleBlockStrategy;
import janggi.piece.Piece;
import janggi.piece.board.Board;
import janggi.position.Position;
import java.util.Set;
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
        final Piece king = new Piece(new PieceMoveRule(pieceType, new ObstacleBlockStrategy()), currentPosition);
        final Position arrivalPosition = new Position(4, 5);
        final PieceMoveRule pieceMoveRule = new PieceMoveRule(PieceType.KING, new ObstacleBlockStrategy());

        // When & Then
        Assertions.assertThatThrownBy(
                        () -> pieceMoveRule.validatePath(currentPosition, arrivalPosition, Board.from(Set.of(king))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 궁성 밖을 나갔습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "CANNON",
            "CHARIOT",
            "CHO_SOLDIER",
            "HAN_SOLDIER",
            "GUARD",
            "KING"
    })
    void 마와_상을_제외한_기물이_궁성_안에_있으면_대각선으로_움직일_수_있다(final PieceType pieceType) {
        // Given
        final PieceMoveRule pieceMoveRule = new PieceMoveRule(pieceType, new ObstacleBlockStrategy());
        final Position currentPosition = new Position(9, 4);
        final Piece piece = new Piece(pieceMoveRule, currentPosition);

        // When & Then
        Assertions.assertThatCode(() -> {
            pieceMoveRule.validatePath(currentPosition, new Position(9, 5), Board.from(Set.of(piece)));
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({
            "HORSE, 8, 5",
            "ELEPHANT, 7, 6"
    })
    void 마와_상은_궁성에_영향을_받지_않는다(final PieceType pieceType, final int arrivalY, final int arrivalX) {
        // Given
        final PieceMoveRule pieceMoveRule = new PieceMoveRule(pieceType, new ObstacleBlockStrategy());
        final Position currentPosition = new Position(10, 4);
        final Piece piece = new Piece(pieceMoveRule, currentPosition);

        // When & Then
        Assertions.assertThatCode(() -> {
            pieceMoveRule.validatePath(currentPosition, new Position(arrivalY, arrivalX), Board.from(Set.of(piece)));
        }).doesNotThrowAnyException();
    }
}
