package domain.board.wing;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.piece.Piece;
import java.util.Map;

public final class RightWing extends Wing {

    public RightWing(WingPieces wingPieces) {
        super(wingPieces);
    }

    @Override
    public Map<Intersection, Piece> setUpPieces(Side side) {
        int row = side.getRowAt(FAR_FROM_BASE_ROW);
        int firstPieceFile = side.getFileAt(new MoveAmount(6));
        int secondPieceFile = side.getFileAt(new MoveAmount(7));

        return Map.of(
                new Intersection(row, firstPieceFile), first,
                new Intersection(row, secondPieceFile), second
        );
    }
}
