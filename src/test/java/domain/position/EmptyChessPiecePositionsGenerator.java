package domain.position;

import domain.chessPiece.ChessPiece;

import java.util.Map;

public class EmptyChessPiecePositionsGenerator implements ChessPiecePositionsGenerator {
    @Override
    public Map<ChessPosition, ChessPiece> generate() {
        return Map.of();
    }
}
