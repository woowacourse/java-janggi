package domain.piece;

import domain.spatial.Position;

public class PieceRemover {

    public void removePieceIfExists(final Pieces pieces, final Position targetPosition) {
        if (pieces.existByPosition(targetPosition)) {
            pieces.deleteByPosition(targetPosition);
        }
    }
}
