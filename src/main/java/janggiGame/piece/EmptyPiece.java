package janggiGame.piece;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.character.PieceType;
import java.util.List;
import java.util.Map;

public class EmptyPiece extends Piece {
    public EmptyPiece() {
        super(Dynasty.EMPTY);
    }

    @Override
    public List<Position> getIntermediatePoints(Position origin, Position destination) {
        return List.of();
    }

    @Override
    public PieceType getType() {
        return PieceType.EMPTY;
    }

    @Override
    public void validateMove(Map<Position, Piece> IntermediatePointsWithPiece, Piece destinationPiece) {
    }
}
