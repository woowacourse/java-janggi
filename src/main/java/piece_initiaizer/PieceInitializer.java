package piece_initiaizer;


import game.Country;
import game.StartingPosition;
import java.util.Map;
import piece.Piece;
import position.Position;

public interface PieceInitializer {

    Map<Position, Piece> init(StartingPosition startingPosition, Country country);
}
