package object.piece;

import java.util.ArrayList;
import java.util.List;
import object.Coordinate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import object.factory.MoveStrategyFactory;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PiecesTest {

    @Test
    void 피스들을_관리한다() {
        // given
        var piece = new Piece(Team.BLUE, MoveStrategyFactory.create(PieceType.CHARIOT), PieceType.CHARIOT,
                new Coordinate(0, 1));
        var piece2 = new Piece(Team.RED, MoveStrategyFactory.create(PieceType.CANNON), PieceType.CANNON,
                new Coordinate(0, 2));

        // when
        Pieces pieces = new Pieces(List.of(piece, piece2));
        var currentPieces = pieces.getPieces();
        // then
        Assertions.assertThatIterable(currentPieces).containsExactlyInAnyOrderElementsOf(List.of(piece, piece2));
    }

    @Test
    void 같은_위치에_있는_적팀_기물을_잡을_수_있다() {
        // given
        var piece1 = new Piece(Team.BLUE, MoveStrategyFactory.create(PieceType.SOLIDER), PieceType.SOLIDER,
                new Coordinate(0, 1));
        var piece2 = new Piece(Team.RED, MoveStrategyFactory.create(PieceType.SOLIDER), PieceType.SOLIDER,
                new Coordinate(0, 1));
        Pieces pieces = new Pieces(new ArrayList<>(List.of(piece1, piece2)));

        // when
        pieces.killPieceFrom(piece1);

        // then
        Assertions.assertThat(pieces.getPieces().size()).isEqualTo(1);
        Assertions.assertThat(pieces.getFirstPiece()).isEqualTo(piece2);
    }

    @Test
    void 같은_위치에_있는_아군은_잡지_않는다() {
        // given
        var piece1 = new Piece(Team.BLUE, MoveStrategyFactory.create(PieceType.SOLIDER), PieceType.SOLIDER,
                new Coordinate(0, 1));
        var piece2 = new Piece(Team.BLUE, MoveStrategyFactory.create(PieceType.SOLIDER), PieceType.SOLIDER,
                new Coordinate(0, 1));
        Pieces pieces = new Pieces(new ArrayList<>(List.of(piece1, piece2)));

        // when
        pieces.killPieceFrom(piece1);

        // then
        Assertions.assertThat(pieces.getPieces().size()).isEqualTo(2);
    }

    @Test
    void 다른_위치의__적군은_잡을_수_없다() {
        // given
        var piece1 = new Piece(Team.BLUE, MoveStrategyFactory.create(PieceType.SOLIDER), PieceType.SOLIDER,
                new Coordinate(0, 1));
        var piece2 = new Piece(Team.RED, MoveStrategyFactory.create(PieceType.SOLIDER), PieceType.SOLIDER,
                new Coordinate(0, 2));
        Pieces pieces = new Pieces(new ArrayList<>(List.of(piece1, piece2)));

        // when
        pieces.killPieceFrom(piece1);

        // then
        Assertions.assertThat(pieces.getPieces().size()).isEqualTo(2);
    }
}
