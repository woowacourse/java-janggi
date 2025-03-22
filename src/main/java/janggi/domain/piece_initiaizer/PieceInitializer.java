package janggi.domain.piece_initiaizer;

import janggi.domain.Country;
import janggi.domain.StartingPosition;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.Map;

public interface PieceInitializer {

    Map<Position, Piece> init(StartingPosition startingPosition, Country country);
}
