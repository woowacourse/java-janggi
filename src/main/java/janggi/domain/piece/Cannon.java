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

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();

        for (int i = position.x() + 2; i <= 8; i++) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.x() + 2; j <= i; j++) {
                rawPositions.add(new RawPosition(j, position.y()));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.x() - 2; i >= 0; i--) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.x() - 2; j >= i; j--) {
                rawPositions.add(new RawPosition(j, position.y()));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.y() + 2; i <= 9; i++) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.y() + 2; j <= i; j++) {
                rawPositions.add(new RawPosition(position.x(), j));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.y() - 2; i >= 0; i--) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.y() - 2; j >= i; j--) {
                rawPositions.add(new RawPosition(position.x(), j));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }
        return rawRoutes;
    }
}
