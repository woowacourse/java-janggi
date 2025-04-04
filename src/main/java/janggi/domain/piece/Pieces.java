package janggi.domain.piece;

import janggi.domain.piece.direction.DiagonalDirection;
import janggi.domain.value.JanggiPosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void movePiece(final Pieces enemyPieces, final JanggiPosition targetPieceJanggiPosition,
            final JanggiPosition destination) {
        Piece target = findTargetPiece(targetPieceJanggiPosition);

        Piece movedTarget = target.move(destination, enemyPieces, this);
        pieces.remove(target);
        pieces.add(movedTarget);
    }

    private Piece findTargetPiece(final JanggiPosition targetPieceJanggiPosition) {
        return pieces.stream().filter(piece -> piece.janggiPosition.equals(targetPieceJanggiPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다."));
    }

    public boolean isNotBlockedBy(final JanggiPosition destination) {
        return pieces.stream()
                .noneMatch(piece -> piece.janggiPosition.equals(destination));
    }

    public boolean isPathBlockedBy(final List<JanggiPosition> pathPositions) {
        return pathPositions.stream()
                .noneMatch(position -> pieces.stream()
                        .anyMatch(piece -> piece.janggiPosition.equals(position)));
    }

    public boolean isPieceExistInRoute(final DiagonalDirection diagonalDirection, final JanggiPosition position) {
        return pieces.stream()
                .anyMatch(piece -> diagonalDirection.isRoute(position, piece.janggiPosition));
    }

    public List<Piece> searchPiecesInPath(final List<JanggiPosition> pathPositions) {
        return pieces.stream()
                .filter(piece -> pathPositions.contains(piece.janggiPosition))
                .toList();
    }

    public void beAttackedAt(final JanggiPosition destination) {
        pieces.removeIf(piece -> piece.janggiPosition.equals(destination));
    }

    public boolean isPieceAlive(final String name) {
        boolean alive = pieces.stream()
                .noneMatch(piece -> piece.pieceType.getName()
                        .equals(name));

        return pieces.isEmpty() || alive;
    }

    public int calculateTotalScore() {
        return pieces.stream()
                .mapToInt(piece -> piece.pieceType.getScore())
                .sum();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
