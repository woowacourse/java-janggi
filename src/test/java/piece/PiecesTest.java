package piece;

import java.util.ArrayList;
import java.util.List;
import move.ChaMoveBehavior;
import move.FoMoveBehavior;
import move.JolMoveBehavior;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import piece.location.Position;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PiecesTest {

    @Test
    void 피스들을_관리한다() {
        // given
        var piece = new Piece(new Position(0, 1), new ChaMoveBehavior(), Team.BLUE);
        var piece2 = new Piece(new Position(0, 2), new FoMoveBehavior(), Team.RED);

        // when
        Pieces pieces = new Pieces(List.of(piece, piece2));
        var currentPieces = pieces.getPieces();
        // then
        Assertions.assertThatIterable(currentPieces).containsExactlyInAnyOrderElementsOf(List.of(piece, piece2));
    }

    @Test
    void 같은_위치에_있는_적팀_기물을_잡을_수_있다() {
        // given
        var piece1 = new Piece(new Position(0, 1), new JolMoveBehavior(), Team.BLUE);
        var piece2 = new Piece(new Position(0, 1), new JolMoveBehavior(), Team.RED);
        Pieces pieces = new Pieces(new ArrayList<>(List.of(piece1)));
        Pieces otherPieces = new Pieces(new ArrayList<>(List.of(piece2)));

        // when
        pieces.killPieceFrom(piece1, otherPieces);

        // then
        Assertions.assertThat(otherPieces.getPieces().size()).isEqualTo(0);
        Assertions.assertThat(pieces.getFirstPiece()).isEqualTo(piece1);
    }

    @Test
    void 같은_위치에_있는_아군은_잡지_않는다() {
        // given
        var piece1 = new Piece(new Position(0, 1), new JolMoveBehavior(), Team.BLUE);
        var piece2 = new Piece(new Position(0, 1), new JolMoveBehavior(), Team.BLUE);
        Pieces pieces = new Pieces(new ArrayList<>(List.of(piece1, piece2)));

        // when
        pieces.killPieceFrom(piece1, new Pieces(new ArrayList<>(pieces.getPieces())));

        // then
        Assertions.assertThat(pieces.getPieces().size()).isEqualTo(2);
    }

    @Test
    void 다른_위치의_적군은_잡을_수_없다() {
        // given
        var piece1 = new Piece(new Position(0, 1), new JolMoveBehavior(), Team.BLUE);
        var piece2 = new Piece(new Position(0, 2), new JolMoveBehavior(), Team.RED);
        Pieces pieces = new Pieces(new ArrayList<>(List.of(piece1, piece2)));

        // when
        pieces.killPieceFrom(piece1, new Pieces(new ArrayList<>(pieces.getPieces())));

        // then
        Assertions.assertThat(pieces.getPieces().size()).isEqualTo(2);
    }
}
