package domain.place;

import domain.place.piece.PieceSymbol;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Place {

    boolean isEmpty();

    boolean isSameSide(Place place);

    boolean hasSide(Side side);

    boolean isSameSymbol(PieceSymbol pieceSymbol);

    List<Position> getPath(Position from);

    String getFormat();

    Optional<Side> getSide();


    boolean canMove(Map<Position, Place> obstacles, Position from, Position to);
}
