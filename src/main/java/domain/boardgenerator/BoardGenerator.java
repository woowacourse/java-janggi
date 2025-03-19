package domain.boardgenerator;

import domain.Position;
import domain.piece.Piece;
import java.util.Map;

public interface BoardGenerator {

    Map<Position, Piece> generateBoard();
}
