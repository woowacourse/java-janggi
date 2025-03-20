package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public Set<Route> getPossibleRoutes(Piece piece) {
        Set<Route> routes = piece.calculateRoutes();
        Set<Route> realRoutes = new HashSet<>();

        for (Route route : routes) {
            boolean check = true;
            for (Piece currentPiece : pieces) {
                if (route.hasPosition(currentPiece)) {
                    if (route.isDestination(currentPiece) && piece.isEnemy(currentPiece)) {
                        continue;
                    }
                    check = false;
                }
            }
            if (check) {
                realRoutes.add(route);
            }
        }
        return realRoutes;
    }

    public Set<Route> getPossibleRoutesForCannon(Piece piece) {
        Set<Route> routes = piece.calculateRoutes();
        Set<Route> realRoutes = new HashSet<>();

        for (Route route : routes) {

            int count = 0;
            for (Piece currentPiece : pieces) {
                if (route.hasPosition(currentPiece)) {
                    if (currentPiece.isCannon()) {
                        break;
                    }
                    if (!route.isDestination(currentPiece)) {
                        count += 1;
                    }
                }
            }
            if (count == 1) {
                realRoutes.add(route);
            }
        }
        return realRoutes;
    }

    public Set<Route> getPossibleRoutesForChariot(Piece piece) {
        Set<Route> routes = piece.calculateRoutes();
        Set<Route> realRoutes = new HashSet<>();

        for (Route route : routes) {
            int count = 0;
            for (Piece currentPiece : pieces) {
                if (route.hasPosition(currentPiece)) {
                    if (route.isDestination(currentPiece) && piece.isEnemy(currentPiece)) {
                        continue;
                    }
                    count++;
                }
            }
            if (count == 0) {
                realRoutes.add(route);
            }
        }
        return realRoutes;
    }

    public Piece findPieceByPositionAndTeam(int x, int y, Team team) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(new Position(x, y)))
                .filter(piece -> piece.isSameTeam(team))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 우리팀 기물이 없습니다."));
    }

    public void move(final Piece piece, final int x, final int y) {
        if (hasPieceByPosition(x, y)) {
            kill(x, y);
        }
        piece.move(new Position(x, y));
    }

    private void kill(int x, int y) {
        pieces.remove(findPieceByPosition(x, y));
    }

    private boolean hasPieceByPosition(int x, int y) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(new Position(x, y)));
    }

    private Piece findPieceByPosition(int x, int y) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(new Position(x, y)))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
    }
}
