package janggi.piece;

import janggi.Position;
import janggi.Side;
import java.util.List;

public class Horse extends Piece {

    public Horse(Side side) {
        super(side);
    }

    @Override
    public boolean movable(Position current, Position destination, List<Position> piecePositions) {
        int differenceX = destination.x() - current.x();
        int differenceY = destination.y() - current.y();

        Position blockingPosition = current;
        if(differenceX == 2) {
            blockingPosition = current.right(1);
        }
        if (differenceX == -2) {
            blockingPosition = current.left(1);
        }
        if(differenceY == 2) {
            blockingPosition = current.up(1);
        }
        if(differenceY == -2) {
            blockingPosition = current.down(1);
        }

        for(Position position: piecePositions){
            if (position.equals(blockingPosition)) {
                return false;
            }
        }
        return true;
    }
}
