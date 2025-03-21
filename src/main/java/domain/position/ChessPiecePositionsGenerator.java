package domain.position;

import domain.chessPiece.ChessPiece;

import java.util.Map;

public interface ChessPiecePositionsGenerator {
    Map<ChessPosition, ChessPiece> generate();
}
