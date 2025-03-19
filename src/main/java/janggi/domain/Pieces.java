package janggi.domain;

import janggi.domain.piece.Piece;
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

                if (currentPiece.isCannon()) {
                    continue;
                }

                if (route.hasPosition(currentPiece) && !route.isDestination(currentPiece)) {
                    count++;
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
}
