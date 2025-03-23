package piece;

import java.util.List;
import java.util.Map;
import move.ChaMoveBehavior;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PositionPiecesTest {

    @Test
    void 보드는_포지션별로_장기판을_관리한다() {
        // given
        Position positionA = new Position(0, 1);
        Position positionB = new Position(0, 2);
        var piece1 = new Piece(positionA, new ChaMoveBehavior(), PieceType.CHA, Team.BLUE);
        var piece2 = new Piece(positionB, new ChaMoveBehavior(), PieceType.CHA, Team.BLUE);
        Pieces pieces = new Pieces(List.of(piece1, piece2));
        PositionPieces positionPieces = new PositionPieces(pieces);
        // when
        Map<Position, Piece> positionPieceMap = positionPieces.positionPieces();
        Map<Position, Piece> expectedPieceMap = Map.of(positionA, piece1, positionB, piece2);

        // then
        Assertions.assertThat(positionPieceMap).isEqualTo(expectedPieceMap);
    }
}
