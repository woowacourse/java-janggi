package domain.board.wing;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public final class LeftWing extends Wing {

    public LeftWing(List<Piece> pieces) {
        super(pieces);
    }

    @Override
    public Map<Intersection, Piece> setUpPieces(Side side) {
        int row = side.getRowAt(FAR_FROM_BASE_ROW);
        int firstPieceFile = side.getFileAt(new MoveAmount(1));
        int secondPieceFile = side.getFileAt(new MoveAmount(2));

        return Map.of(
                new Intersection(row, firstPieceFile), first,
                new Intersection(row, secondPieceFile), second
        );
    }
}
