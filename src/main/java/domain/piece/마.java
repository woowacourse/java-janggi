package domain.piece;

import domain.pattern.마Path;
import domain.piece.state.Moved마;

public class 마 extends Piece {

    public 마(Side side) {
        super(5, side, new 마Path(), new Moved마());
    }

}
