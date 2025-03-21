package domain.position;

import domain.type.ChessPieceType;
import domain.chessPiece.*;

import java.util.*;

public class ChessPiecePositions {

    private final Map<ChessPosition, ChessPiece> chessPieces;

    private ChessPiecePositions(final Map<ChessPosition, ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public static ChessPiecePositions empty() {
        return new ChessPiecePositions(new HashMap<>());
    }

    public static ChessPiecePositions from(final Map<ChessPosition, ChessPiece> chessPieces) {
        return new ChessPiecePositions(chessPieces);
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

    public boolean existChessPieceByPosition(final ChessPosition position) {
        return chessPieces.containsKey(position);
    }

    public ChessPiece getChessPieceByPosition(final ChessPosition position) {
        if (!existChessPieceByPosition(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다");
        }
        return chessPieces.get(position);
    }

    public ChessPieceType getChessPieceTypeByPosition(final ChessPosition position) {
        return getChessPieceByPosition(position).getChessPieceType();
    }

    public void moveChessPiece(final ChessPosition position, final ChessPiece piece) {
        chessPieces.put(position, piece);
    }

    public void removeChessPiece(final ChessPosition position) {
        chessPieces.remove(position);

    }

}
