package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawPosition;
import janggi.domain.position.RawRoute;
import janggi.domain.routePolicy.RoutePolicyForNormal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(final Position position, final Team team) {
        super(team, position, PieceType.CHARIOT, new RoutePolicyForNormal());
    }

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();

        rawRoutes.addAll(makeRightRoutes());
        rawRoutes.addAll(makeLeftRoutes());
        rawRoutes.addAll(makeUpRoutes());
        rawRoutes.addAll(makeDownRoutes());

        return rawRoutes;
    }

    private Set<RawRoute> makeDownRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();
        for (int i = position.y() + 1; i <= Board.MAX_Y_POSITION; i++) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.y() + 1; j <= i; j++) {
                rawPositions.add(new RawPosition(position.x(), j));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }
        return rawRoutes;
    }

    private Set<RawRoute> makeUpRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();
        for (int i = position.y() - 1; i >= Board.MIN_POSITION; i--) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.y() - 1; j >= i; j--) {
                rawPositions.add(new RawPosition(position.x(), j));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }
        return rawRoutes;
    }

    private Set<RawRoute> makeLeftRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();
        for (int i = position.x() - 1; i >= Board.MIN_POSITION; i--) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.x() - 1; j >= i; j--) {
                rawPositions.add(new RawPosition(j, position.y()));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }
        return rawRoutes;
    }

    private Set<RawRoute> makeRightRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();
        for (int i = position.x() + 1; i <= Board.MAX_X_POSITION; i++) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.x() + 1; j <= i; j++) {
                rawPositions.add(new RawPosition(j, position.y()));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }
        return rawRoutes;
    }

    @Override
    protected Set<RawRoute> calculateAdditionalRawRoutesInPalace() {
        return Set.of(
                new RawRoute(List.of(position.upRightDiagonal())),
                new RawRoute(List.of(position.downLeftDiagonal())),
                new RawRoute(List.of(position.downRightDiagonal())),
                new RawRoute(List.of(position.upLeftDiagonal())),
                new RawRoute(List.of(position.upRightDiagonal(), position.upRightDiagonal().upRightDiagonal())),
                new RawRoute(List.of(position.downLeftDiagonal(), position.downLeftDiagonal().downLeftDiagonal())),
                new RawRoute(List.of(position.downRightDiagonal(), position.downRightDiagonal().downRightDiagonal())),
                new RawRoute(List.of(position.upLeftDiagonal(), position.upLeftDiagonal().upLeftDiagonal()))
        );
    }
}
