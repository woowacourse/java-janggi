package domain.position;

import domain.chessPiece.*;

import java.util.HashMap;
import java.util.Map;

public class InitialChessPiecePositionsGenerator implements ChessPiecePositionsGenerator {
    @Override
    public Map<ChessPosition, ChessPiece> generate() {
        Map<ChessPosition, ChessPiece> chessPieces = new HashMap<>();
        chessPieces.putAll(Cannon.initPieces());
        chessPieces.putAll(Chariot.initPieces());
        chessPieces.putAll(Elephant.initPieces());
        chessPieces.putAll(Horse.initPieces());
        chessPieces.putAll(Pawn.initPieces());
        return chessPieces;
    }
}
