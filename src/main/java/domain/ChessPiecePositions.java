package domain;

import java.util.*;

public class ChessPiecePositions {

    private final Map<ChessPosition, ChessPiece> chessPieces = new HashMap<>();

    private ChessPiecePositions(final Map<ChessPosition, ChessPiece> chessPieces) {
        this.chessPieces.putAll(chessPieces);
    }

    public static ChessPiecePositions empty() {
        return new ChessPiecePositions(new HashMap<>());
    }

    public void initialize() {
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
    }

    public Map<ChessPosition, ChessPiece> getChessPieces() {
        return Collections.unmodifiableMap(chessPieces);
    }
}
