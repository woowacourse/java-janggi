package domain.position;

import domain.chessPiece.ChessPiece;

import java.util.Collections;
import java.util.Map;

public class ChessPiecePositions {

    private final Map<ChessPosition, ChessPiece> chessPieces;

    public ChessPiecePositions(ChessPiecePositionsGenerator generator) {
        this.chessPieces = generator.generate();
    }

    public boolean existChessPieceByPosition(final ChessPosition position) {
        return chessPieces.containsKey(position);
    }

    public ChessPiece getChessPieceByPosition(final ChessPosition position) {
        validateExistPiece(position);
        return chessPieces.get(position);
    }

    public void move(final ChessPosition from, final ChessPosition to) {
        validateExistPiece(from);
        validateEmptyPosition(to);
        ChessPiece target = getChessPieceByPosition(from);
        removeChessPieceByPosition(from);
        putChessPiece(to, target);
    }

    private void validateExistPiece(final ChessPosition position) {
        if (!existChessPieceByPosition(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
    }

    private void validateEmptyPosition(final ChessPosition position) {
        if (existChessPieceByPosition(position)) {
            throw new IllegalArgumentException("해당 위치에 이미 다른 기물이 존재합니다.");
        }
    }

    public void removeChessPieceByPosition(final ChessPosition position) {
        validateExistPiece(position);
        chessPieces.remove(position);
    }

    private void putChessPiece(final ChessPosition position, final ChessPiece chessPiece) {
        validateEmptyPosition(position);
        chessPieces.put(position, chessPiece);
    }

    public Map<ChessPosition, ChessPiece> getChessPieces() {
        return Collections.unmodifiableMap(chessPieces);
    }
}
