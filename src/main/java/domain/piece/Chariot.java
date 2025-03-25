package domain.piece;

import domain.pattern.ChariotPath;
import domain.piece.state.MovedChariot;

public class Chariot extends Piece {
    public Chariot(Side side) {
        super(13, side, new ChariotPath(), new MovedChariot());
    }
}
