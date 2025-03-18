package chess.domain;

import java.util.Map;

public abstract class Pawn implements Piece {

    public boolean isMovable(Map<int[], Piece> pieces, int[] currentPoint, int[] destination) {
        if(pieces.containsKey(currentPoint) && isNotRemovable(pieces.get(destination))) {
            return false;
        }

        return isMovable(currentPoint, destination);
    }

    protected abstract boolean isNotRemovable(Piece piece);

    protected abstract boolean isMovable(int[] currentPoint, int[] destination);
}
