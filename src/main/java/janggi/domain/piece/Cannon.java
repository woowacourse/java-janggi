package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawPosition;
import janggi.domain.position.RawRoute;
import janggi.domain.routePolicy.RoutePolicyForCannon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(final Position position, final Team team) {
        super(team, position, PieceType.CANNON, new RoutePolicyForCannon());
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

    private Set<RawRoute> makeUpRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();
        List<RawPosition> rawPositions = new ArrayList<>();
        RawPosition current = position.up();

        while (current.y() <= Board.MAX_Y_POSITION) {
            rawPositions.add(current);
            rawRoutes.add(new RawRoute(new ArrayList<>(rawPositions)));
            current = current.up();
        }
        return rawRoutes;
    }

    private Set<RawRoute> makeDownRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();
        List<RawPosition> rawPositions = new ArrayList<>();
        RawPosition current = position.down();

        while (current.y() >= Board.MIN_POSITION) {
            rawPositions.add(current);
            rawRoutes.add(new RawRoute(new ArrayList<>(rawPositions)));
            current = current.down();
        }
        return rawRoutes;
    }

    private Set<RawRoute> makeLeftRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();
        List<RawPosition> rawPositions = new ArrayList<>();
        RawPosition current = position.left();

        while (current.x() >= Board.MIN_POSITION) {
            rawPositions.add(current);
            rawRoutes.add(new RawRoute(new ArrayList<>(rawPositions)));
            current = current.left();
        }
        return rawRoutes;
    }

    private Set<RawRoute> makeRightRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();
        List<RawPosition> rawPositions = new ArrayList<>();
        RawPosition current = position.right();

        while (current.x() <= Board.MAX_X_POSITION) {
            rawPositions.add(current);
            rawRoutes.add(new RawRoute(new ArrayList<>(rawPositions)));
            current = current.right();
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
