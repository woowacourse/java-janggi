package piece;

import position.Position;
import route.Routes;

public class Palace extends Piece {

    public Palace(Position position) {
        super(position, Routes.ofPalace());
    }

    @Override
    public PieceType type() {
        return PieceType.PALACE;
    }
}
