package piece;


import game.Country;
import game.StartingPosition;
import java.util.Map;
import position.Position;

public interface PieceInitializer {

    Map<Position, Piece> init(StartingPosition startingPosition, Country country);
}
