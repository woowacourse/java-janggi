package domain.boardgenerator;

import domain.piece.Piece;
import java.util.List;

public interface BoardGenerator {

    List<Piece> generateBoard();
}
