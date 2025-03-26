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
import piece.player.Team;
import piece.position.JanggiPosition;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PiecesTest {

    @Test
    void 피스를_움직일_수_있다() {
        var piece = new Piece(new JanggiPosition(0, 1), new ChaMoveBehavior(), Team.BLUE);

        Pieces pieces = new Pieces(List.of(piece));
        pieces.move(new JanggiPosition(0, 1), new JanggiPosition(3, 1), pieces);
        var currentPieces = pieces.getPieces();

        var expecetedPiece = new Piece(new JanggiPosition(3, 1), new ChaMoveBehavior(), Team.BLUE);

        Assertions.assertThatIterable(currentPieces).containsExactlyInAnyOrderElementsOf(List.of(expecetedPiece));
    }

    @Test
    void 피스들을_관리한다() {
        var piece = new Piece(new JanggiPosition(0, 1), new ChaMoveBehavior(), Team.BLUE);
        var piece2 = new Piece(new JanggiPosition(0, 2), new FoMoveBehavior(), Team.RED);

        Pieces pieces = new Pieces(List.of(piece, piece2));
        var currentPieces = pieces.getPieces();
        Assertions.assertThatIterable(currentPieces).containsExactlyInAnyOrderElementsOf(List.of(piece, piece2));
    }

    @Test
    void 같은_위치에_있는_적팀_기물을_잡을_수_있다() {
        var piece1 = new Piece(new JanggiPosition(0, 1), new JolMoveBehavior(), Team.BLUE);
        var piece2 = new Piece(new JanggiPosition(0, 1), new JolMoveBehavior(), Team.RED);
        Pieces pieces = new Pieces(new ArrayList<>(List.of(piece1)));
        Pieces otherPieces = new Pieces(new ArrayList<>(List.of(piece2)));

        pieces.killPieceFrom(piece1, otherPieces);

        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(otherPieces.getPieces().size()).isEqualTo(0),
                    () -> Assertions.assertThat(pieces.getFirstPiece()).isEqualTo(piece1)
            );
        }).doesNotThrowAnyException();
    }

    @Test
    void 같은_위치에_있는_아군은_잡지_않는다() {
        var piece1 = new Piece(new JanggiPosition(0, 1), new JolMoveBehavior(), Team.BLUE);
        var piece2 = new Piece(new JanggiPosition(0, 1), new JolMoveBehavior(), Team.BLUE);
        Pieces pieces = new Pieces(new ArrayList<>(List.of(piece1, piece2)));

        pieces.killPieceFrom(piece1, new Pieces(new ArrayList<>(pieces.getPieces())));

        Assertions.assertThat(pieces.getPieces().size()).isEqualTo(2);
    }

    @Test
    void 다른_위치의_적군은_잡을_수_없다() {
        var piece1 = new Piece(new JanggiPosition(0, 1), new JolMoveBehavior(), Team.BLUE);
        var piece2 = new Piece(new JanggiPosition(0, 2), new JolMoveBehavior(), Team.RED);
        Pieces pieces = new Pieces(new ArrayList<>(List.of(piece1, piece2)));

        pieces.killPieceFrom(piece1, new Pieces(new ArrayList<>(pieces.getPieces())));

        Assertions.assertThat(pieces.getPieces().size()).isEqualTo(2);
    }
}
