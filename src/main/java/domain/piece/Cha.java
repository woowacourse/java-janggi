package domain.piece;

import domain.Position;

public class Cha extends ActivePiece{



    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }
}
