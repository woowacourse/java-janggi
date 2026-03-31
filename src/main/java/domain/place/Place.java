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

    String getFormat();

    Optional<Side> getSide();

    List<Position> getNormalPath(Position from);

    List<Position> getPalacePath(Position from);

    boolean canNormalMove(Map<Position, Place> obstacles, Position from, Position to);

    boolean canPalaceMove(Map<Position, Place> obstacles, Position from, Position to);
}
