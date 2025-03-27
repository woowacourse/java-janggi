package domain.piece;

import domain.pattern.Direction;
import domain.pattern.Path;
import domain.piece.state.MovedSoldierByeong;
import domain.piece.state.MovedSoldierJol;

public class Soldier extends Piece {

    public Soldier(Side side) {
        super(2, side, new Path(Direction.createJolPatternMap()), new MovedSoldierJol());
        if (side == Side.HAN) {
            path = new Path(Direction.createByeongPatternMap());
            state = new MovedSoldierByeong();
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        if (side == Side.HAN) {
            return PieceSymbol.SOLDIER_BYEONG;
        }
        return PieceSymbol.SOLDIER_JOL;
    }
}
