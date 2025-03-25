package janggi.piece;

import janggi.piece.direction.DiagonalDirection;
import janggi.value.JanggiPosition;
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

    private Piece findTargetPiece(JanggiPosition targetPieceJanggiPosition) {
        return pieces.stream().filter(piece -> piece.getPosition().equals(targetPieceJanggiPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다."));
    }

    public boolean isNotBlockedBy(JanggiPosition destination) {
        return pieces.stream()
                .noneMatch(piece -> piece.getPosition().equals(destination));
    }

    public boolean isPathBlockedBy(List<JanggiPosition> pathPositions) {
        return pathPositions.stream()
                .noneMatch(position -> pieces.stream()
                        .anyMatch(piece -> piece.getPosition().equals(position)));
    }

    public boolean isPieceExistInRoute(DiagonalDirection diagonalDirection, JanggiPosition position) {
        return pieces.stream()
                .anyMatch(piece -> diagonalDirection.isRoute(position, piece.getPosition()));
    }

    public List<Piece> searchPiecesInPath(List<JanggiPosition> pathPositions) {
        return pieces.stream()
                .filter(piece -> pathPositions.contains(piece.getPosition()))
                .toList();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
