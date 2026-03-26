package domain;

import java.util.Map;

public class Gung extends PieceImpl{

    public Gung(Side side) {
        super(side);
    }

    @Override
    public boolean isMovable(Map<Position, Piece> pieces, Position departure, Position destination) {
        // 상 하 좌 우
        Position up = new Position(departure.row() + 1, departure.column());
//        Position down
//        Position right
//        Position left
        return false;
    }
}
