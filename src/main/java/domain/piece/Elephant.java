package domain.piece;

import domain.pattern.ElephantPath;
import domain.piece.state.MovedElephant;

public class Elephant extends Piece {
    public Elephant(Side side) {
        super(3, side, new ElephantPath(), new MovedElephant());
    }
}
