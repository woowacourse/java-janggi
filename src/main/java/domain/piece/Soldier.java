package domain.piece;

import domain.pattern.SoldierByeongPath;
import domain.pattern.SoldierJolPath;
import domain.piece.state.MovedSoldierByeong;
import domain.piece.state.MovedSoldierJol;

public class Soldier extends Piece {

    public Soldier(Side side) {
        super(2, side, new SoldierJolPath(), new MovedSoldierJol());
        if (side == Side.HAN) {
            path = new SoldierByeongPath();
            state = new MovedSoldierByeong();
        }
    }
}
