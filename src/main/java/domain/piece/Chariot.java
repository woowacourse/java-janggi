package domain.piece;

import domain.pattern.Direction;
import domain.pattern.Path;
import domain.piece.state.MovedChariot;

public class Chariot extends Piece {
    public Chariot(Side side) {
        super(13, side, new Path(Direction.createChariotOrCannonPatternMap()), new MovedChariot());
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CHARIOT;
    }
}
