package janggi.piece;

import janggi.piece.direction.MaDirection;
import janggi.piece.direction.SangDirection;
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

        pieces.remove(target);
        Piece movedTarget = target.move(destination, enemyPieces, this);
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

    public boolean isPieceExistInRouteMa(MaDirection maDirection, JanggiPosition position) {
        return pieces.stream()
                .anyMatch(piece -> maDirection.isDirectRoute(position, piece.getPosition()));
    }

    public boolean isPieceExistInRouteSang(SangDirection sangDirection, JanggiPosition position) {
        return pieces.stream()
                .anyMatch(piece -> sangDirection.isRoute(position, piece.getPosition()));
    }

    public boolean isPieceInPathEmpty(List<JanggiPosition> path, List<Piece> enemy) {
        List<Piece> alliesInPath = searchPiecesInPath(pieces, path);
        List<Piece> enemyInPath = searchPiecesInPath(enemy, path);

        return !alliesInPath.isEmpty() || !enemyInPath.isEmpty();
    }

    public boolean isPoInPath(List<JanggiPosition> path, List<Piece> enemy) {
        List<Piece> alliesInPath = searchPiecesInPath(pieces, path);
        List<Piece> enemyInPath = searchPiecesInPath(enemy, path);

        return alliesInPath.stream().anyMatch(piece -> piece.checkPieceType(PieceType.PO)) ||
                enemyInPath.stream().anyMatch(piece -> piece.checkPieceType(PieceType.PO));
    }

    public boolean isOnlyOnePieceInPath(List<JanggiPosition> path, List<Piece> enemy, List<Piece> allies) {
        List<Piece> alliesInPath = searchPiecesInPath(allies, path);
        List<Piece> enemyInPath = searchPiecesInPath(enemy, path);

        return alliesInPath.size() + enemyInPath.size() == 1;
    }

    private List<Piece> searchPiecesInPath(List<Piece> pieces, List<JanggiPosition> pathPositions) {
        return pieces.stream()
                .filter(piece -> pathPositions.contains(piece.getPosition()))
                .toList();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
