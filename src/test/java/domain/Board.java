package domain;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<ChessPosition, ChessPiece> chessPieces;

    public Board(final Map<ChessPosition, ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public static Board initialize() {
        Map<ChessPosition, ChessPiece> chessPieces = new HashMap<>();
        for (ChessPieceType chessPieceType : ChessPieceType.values()) {
            for (ChessPosition chessPosition : chessPieceType.getBluePosition()) {
                chessPieces.put(chessPosition, chessPieceType.generateChessPiece(ChessTeam.BLUE));
            }

            for (ChessPosition chessPosition : chessPieceType.getRedPosition()) {
                chessPieces.put(chessPosition, chessPieceType.generateChessPiece(ChessTeam.RED));
            }
        }
        return new Board(chessPieces);
    }


    public Map<ChessPosition, ChessPiece> getChessPieces() {
        return chessPieces;
    }
}
