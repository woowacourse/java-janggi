package domain.piece;

import domain.pattern.Direction;
import domain.pattern.Path;
import domain.piece.state.MovedElephant;

public class Elephant extends Piece {
    public Elephant(Side side) {
        super(3, side, new Path(Direction.createElephantPatternMap()), new MovedElephant());
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.ELEPHANT;
    }
}
