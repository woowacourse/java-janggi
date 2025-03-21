package domain.piece;

import domain.pattern.사Path;
import domain.piece.state.Moved사;

public class 사 extends Piece {
    public 사(Side side) {
        super(3, side, new 사Path(), new Moved사());
    }
}
