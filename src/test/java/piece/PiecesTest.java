package piece;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import strategy.MoveStrategyFactory;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PiecesTest {

    @Test
    void 피스들을_관리한다() {
        // given
        var piece = new Piece(new Position(0, 1), MoveStrategyFactory.create(PieceType.CHA), PieceType.CHA);
        var piece2 = new Piece(new Position(0, 2), MoveStrategyFactory.create(PieceType.FO), PieceType.FO);

        // when
        Pieces pieces = new Pieces(List.of(piece, piece2));
        var currentPieces = pieces.getPieces();
        // then
        Assertions.assertThatIterable(currentPieces).containsExactlyInAnyOrderElementsOf(List.of(piece, piece2));
    }

}
