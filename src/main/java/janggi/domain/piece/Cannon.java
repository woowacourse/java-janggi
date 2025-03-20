package janggi.domain.piece;

import janggi.domain.RawRoute;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    protected Set<RawRoute> calculateRawRoutes() {
        final Set<RawRoute> rawRoutes = new HashSet<>();

        for (int i = position.x() + 1; i <= 8; i++) {
            final List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.x() + 1; j <= i; j++) {
                rawPositions.add(new RawPosition(j, position.y()));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.x() - 1; i >= 0; i--) {
            final List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.x() - 1; j >= i; j--) {
                rawPositions.add(new RawPosition(j, position.y()));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.y() + 1; i <= 9; i++) {
            final List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.y() + 1; j <= i; j++) {
                rawPositions.add(new RawPosition(position.x(), j));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.y() - 1; i >= 0; i--) {
            final List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.y() - 1; j >= i; j--) {
                rawPositions.add(new RawPosition(position.x(), j));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }
        return rawRoutes;
    }
}
