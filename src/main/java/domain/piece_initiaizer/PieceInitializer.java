package domain.piece_initiaizer;

import domain.Country;
import domain.StartingPosition;
import domain.piece.Piece;
import domain.position.Position;

import java.util.Map;

public interface PieceInitializer {

    Map<Position, Piece> init(StartingPosition startingPosition, Country country);
}
