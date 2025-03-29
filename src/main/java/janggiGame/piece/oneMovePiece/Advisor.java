package janggiGame.piece.oneMovePiece;

import janggiGame.piece.Dynasty;
import janggiGame.piece.Type;
import janggiGame.position.Position;
import java.util.List;

public class Advisor extends OneMovePiece {

    public Advisor(Dynasty dynasty) {
        super(dynasty, Type.ADVISOR);
    }

    @Override
    public List<Position> getRoute(Position origin, Position destination) {
        validateDestinationInPalace(destination);
        return super.getRoute(origin, destination);
    }
}
