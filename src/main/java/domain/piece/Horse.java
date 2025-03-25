package domain.piece;

import domain.pattern.HorsePath;
import domain.piece.state.MovedHorse;

public class Horse extends Piece {

    public Horse(Side side) {
        super(5, side, new HorsePath(), new MovedHorse());
    }
}
