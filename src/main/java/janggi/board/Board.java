package janggi.board;

import janggi.direction.PieceType;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(final Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public static Board from(final Set<Piece> givenPieces) {
        return new Board(givenPieces.stream()
                .collect(Collectors.toMap(Piece::getPosition, Function.identity())));
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
        final Piece targetPiece = findPieceByPosition(currentPosition);
        targetPiece.updatePosition(arrivalPosition);
        pieces.remove(currentPosition);
        pieces.put(arrivalPosition, targetPiece);
    }

    public void swapPieces(final Position firstPosition, final Position secondPosition) {
        final Piece firstPiece = findPieceByPosition(firstPosition);
        final Piece secondPiece = findPieceByPosition(secondPosition);

        firstPiece.updatePosition(secondPosition);
        secondPiece.updatePosition(firstPosition);

        pieces.put(firstPosition, secondPiece);
        pieces.put(secondPosition, firstPiece);
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
}
