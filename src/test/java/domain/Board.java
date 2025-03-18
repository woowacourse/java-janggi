package domain;

import java.util.Map;

public class Board {
    private final Map<ChessPosition, ChessPiece> chessPieces;

    public Board(final Map<ChessPosition, ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public static Board initialize() {
        return new Board(Map.of(new ChessPosition(0,0), new Pawn(ChessPieceType.PAWN, ChessTeam.RED)));
    }


    public Map<ChessPosition, ChessPiece> getChessPieces() {
        return chessPieces;
    }
}
