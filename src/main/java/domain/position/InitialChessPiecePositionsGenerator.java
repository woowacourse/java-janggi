package domain.position;

import domain.chessPiece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitialChessPiecePositionsGenerator implements ChessPiecePositionsGenerator {
    @Override
    public Map<ChessPosition, ChessPiece> generate() {
        Map<ChessPosition, ChessPiece> chessPieces = new HashMap<>();
        List<ChessPiece> pieces = new ArrayList<>();
        pieces.addAll(Cannon.initPieces());
        pieces.addAll(Chariot.initPieces());
        pieces.addAll(Elephant.initPieces());
        pieces.addAll(Guard.initPieces());
        pieces.addAll(Horse.initPieces());
        pieces.addAll(King.initPieces());
        pieces.addAll(Pawn.initPieces());
        for (ChessPiece piece : pieces) {
            chessPieces.put(piece.getPosition(), piece);
        }
        return chessPieces;
    }
}
