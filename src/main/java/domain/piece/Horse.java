package domain.piece;

import domain.pattern.Direction;
import domain.pattern.Path;
import domain.piece.state.MovedHorse;

public class Horse extends Piece {

    public Horse(Side side) {
        super(5, side, new Path(Direction.createHorsePatternMap()), new MovedHorse());
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.HORSE;
    }
}
