package janggi.piece;

import janggi.direction.PieceMovement;
import janggi.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

// 기물들을 관리한다.
public class Pieces {

    private final Map<Position, Piece> pieces;

    public Pieces(final Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public static Pieces from(final Set<Piece> givenPieces) {
        return new Pieces(givenPieces.stream()
                .collect(Collectors.toMap(Piece::getPosition, Function.identity())));
    }

    public void validatePath(final Position currentPosition, final Position arrivalPosition,
                             final Set<Piece> totalPieces) {
        final Piece piece = findPieceByPosition(currentPosition);
        piece.validateMovement(currentPosition, arrivalPosition, totalPieces);
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
                .anyMatch(piece -> piece.matchPieceMovement(PieceMovement.KING));
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

    public List<Piece> getPieces() {
        return pieces.values()
                .stream()
                .toList();
    }
}
