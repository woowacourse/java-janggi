package domain.position;

import domain.chessPiece.ChessPiece;
import java.util.List;

public interface ChessPiecePositionsGenerator {
    List<ChessPiece> generate();
}
