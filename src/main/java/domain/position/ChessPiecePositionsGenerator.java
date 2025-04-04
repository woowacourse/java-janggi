package domain.position;

import domain.chesspiece.ChessPiece;
import java.util.List;

public interface ChessPiecePositionsGenerator {
    List<ChessPiece> generate();
}
