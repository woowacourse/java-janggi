package domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChessPiecePositions {

    private final Map<ChessPosition, ChessPiece> chessPieces;

    private ChessPiecePositions(final Map<ChessPosition, ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public static ChessPiecePositions empty() {
        return new ChessPiecePositions(new HashMap<>());
    }

    public void initialize() {
        for (ChessPieceType chessPieceType : ChessPieceType.values()) {
            for (ChessPosition chessPosition : chessPieceType.getBluePosition()) {
                chessPieces.put(chessPosition, chessPieceType.generateChessPiece(ChessTeam.BLUE));
            }

            for (ChessPosition chessPosition : chessPieceType.getRedPosition()) {
                chessPieces.put(chessPosition, chessPieceType.generateChessPiece(ChessTeam.RED));
            }
        }
    }

    public Map<ChessPosition, ChessPiece> getChessPieces() {
        return Collections.unmodifiableMap(chessPieces);
    }
}
