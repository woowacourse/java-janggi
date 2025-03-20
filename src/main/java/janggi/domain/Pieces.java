package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Pieces {

    private static final int REQUIRED_JUMP_PIECES_FOR_CANNON = 1;

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public Set<Route> getPossibleRoutes(final Piece piece) {
        return piece.calculateRoutes().stream()
                .filter(route -> isValidNormalRoute(route, piece))
                .collect(Collectors.toSet());
    }

    private boolean isValidNormalRoute(final Route route, final Piece piece) {
        return pieces.stream()
                .filter(route::hasPosition)
                .allMatch(currentPiece -> route.isDestination(currentPiece) && piece.isEnemy(currentPiece));
    }

    public Set<Route> getPossibleRoutesForCannon(final Piece piece) {
        return piece.calculateRoutes().stream()
                .filter(this::isValidCannonRoute)
                .collect(Collectors.toSet());
    }

    private boolean isValidCannonRoute(final Route route) {
        return countPiecesInRoute(route) == REQUIRED_JUMP_PIECES_FOR_CANNON;
    }

    private int countPiecesInRoute(final Route route) {
        final long cannonOrDestinationCount = pieces.stream()
                .filter(route::hasPosition)
                .filter(currentPiece -> currentPiece.isCannon() || route.isDestination(currentPiece))
                .count();

        if (cannonOrDestinationCount > 0) {
            return 0;
        }

        return (int) pieces.stream()
                .filter(route::hasPosition)
                .count();
    }

    public Set<Route> getPossibleRoutesForChariot(final Piece piece) {
        return piece.calculateRoutes().stream()
                .filter(route -> isValidChariotRoute(route, piece))
                .collect(Collectors.toSet());
    }

    private boolean isValidChariotRoute(final Route route, final Piece piece) {
        return pieces.stream()
                .filter(route::hasPosition)
                .allMatch(currentPiece -> route.isDestination(currentPiece) && piece.isEnemy(currentPiece));
    }

    public Piece findPieceByPositionAndTeam(final Position position, final Team team) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .filter(piece -> piece.isSameTeam(team))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 우리팀 기물이 없습니다."));
    }

    public void move(final Position position, final Piece piece) {
        if (hasPieceByPosition(position.x(), position.y())) {
            kill(position.x(), position.y());
        }
        piece.move(position);
    }

    private void kill(final int x, final int y) {
        pieces.remove(findPieceByPosition(x, y));
    }

    private boolean hasPieceByPosition(final int x, final int y) {
        final Position position = new Position(x, y);
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(position));
    }

    private Piece findPieceByPosition(final int x, final int y) {
        final Position position = new Position(x, y);
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
