package janggi.piece.board;

import janggi.direction.PieceType;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(final Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void validatePath(final Position currentPosition, final Position arrivalPosition,
                             final Board totalBoard) {
        final Piece piece = findPieceByPosition(currentPosition);
        piece.validateMovement(currentPosition, arrivalPosition, totalBoard);
    }

    public Piece findPieceByPosition(final Position position) {
        if (pieces.containsKey(position)) {
            return pieces.get(position);
        }
        throw new IllegalArgumentException("[ERROR] 해당 좌표에 자신의 팀 기물이 존재하지 않습니다.");
    }

    public boolean hasPiece(final Position position) {
        return pieces.containsKey(position);
    }

    public boolean hasKing() {
        return pieces.values().stream()
                .anyMatch(piece -> piece.matchPieceMovement(PieceType.KING));
    }

    public void removePiece(final Position position) {
        pieces.remove(position);
    }

    public void updatePiece(final Position currentPosition, final Position arrivalPosition) {
        final Piece currentPiece = findPieceByPosition(currentPosition);
        pieces.remove(currentPosition);
        pieces.put(arrivalPosition, currentPiece);
    }

    public void swapPieces(final Position firstPosition, final Position secondPosition) {
        final Piece firstPiece = findPieceByPosition(firstPosition);
        final Piece secondPiece = findPieceByPosition(secondPosition);

        pieces.put(firstPosition, secondPiece);
        pieces.put(secondPosition, firstPiece);
    }

    public Board addAll(final Board givenBoard) {
        final Map<Position, Piece> pieces = new HashMap<>(getBoard());
        pieces.putAll(givenBoard.getBoard());
        return new Board(pieces);
    }

    public Double calculateScore() {
        return pieces.values().stream()
                .mapToDouble(piece -> piece.getPieceType().getScore())
                .sum();
    }

    public List<Piece> getPieces() {
        return pieces.values()
                .stream()
                .toList();
    }

    public List<Position> getPositions() {
        return pieces.keySet()
                .stream()
                .toList();
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(pieces);
    }
}
