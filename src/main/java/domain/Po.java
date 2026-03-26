package domain;

import java.util.Map;

public class Po extends PieceImpl {

    public Po(Side side) {
        super(side);
    }

    @Override
    public boolean isMovable(Map<Position, Piece> pieces, Position departure,
                             Position destination) {
        return false;
    }

    @Override
    public boolean isPo() {
        return true;
    }
}
