package domain;

import java.util.Map;

public class Board {
    private final Map<ChessPosition, ChessPiece> chessPieces;

    public Board(final Map<ChessPosition, ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public static Board initialize() {
        throw new UnsupportedOperationException("Unsupported initialize");
    }


    public Map<ChessPosition, ChessPiece> getChessPieces() {
        return null;
    }
}
