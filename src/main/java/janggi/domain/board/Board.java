package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.position.Position;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(final Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void move(final Position from, final Position to, final Board totalBoard) {
        final Piece piece = findPieceByPosition(from);
        piece.validateMovement(from, to, totalBoard);
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
                .anyMatch(piece -> piece == Piece.KING);
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
                .mapToDouble(Piece::getScore)
                .sum();
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
