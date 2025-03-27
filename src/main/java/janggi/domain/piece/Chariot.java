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
        super(position, team, new RoutePolicyForNormal());
        this.pieceType = PieceType.CHARIOT;
    }

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        Set<RawRoute> rawRoutes = new HashSet<>();

        for (int i = position.x() + 1; i <= Board.MAX_X_POSITION; i++) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.x() + 1; j <= i; j++) {
                rawPositions.add(new RawPosition(j, position.y()));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.x() - 1; i >= Board.MIN_POSITION; i--) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.x() - 1; j >= i; j--) {
                rawPositions.add(new RawPosition(j, position.y()));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.y() + 1; i <= Board.MAX_Y_POSITION; i++) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.y() + 1; j <= i; j++) {
                rawPositions.add(new RawPosition(position.x(), j));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }

        for (int i = position.y() - 1; i >= Board.MIN_POSITION; i--) {
            List<RawPosition> rawPositions = new ArrayList<>();
            for (int j = position.y() - 1; j >= i; j--) {
                rawPositions.add(new RawPosition(position.x(), j));
            }
            rawRoutes.add(new RawRoute(rawPositions));
        }
        return rawRoutes;
    }

    @Override
    protected Set<RawRoute> calculateAdditionalRawRoutesInPalace() {
        return Set.of(
                new RawRoute(List.of(new RawPosition(position.x() + 1, position.y() + 1))),
                new RawRoute(List.of(new RawPosition(position.x() - 1, position.y() - 1))),
                new RawRoute(List.of(new RawPosition(position.x() + 1, position.y() - 1))),
                new RawRoute(List.of(new RawPosition(position.x() - 1, position.y() + 1))),
                new RawRoute(List.of(new RawPosition(position.x() + 1, position.y() + 1),
                        new RawPosition(position.x() + 2, position.y() + 2))),
                new RawRoute(List.of(new RawPosition(position.x() - 1, position.y() - 1),
                        new RawPosition(position.x() - 2, position.y() - 2))),
                new RawRoute(List.of(new RawPosition(position.x() + 1, position.y() - 1),
                        new RawPosition(position.x() + 2, position.y() - 2))),
                new RawRoute(List.of(new RawPosition(position.x() - 1, position.y() + 1),
                        new RawPosition(position.x() - 2, position.y() + 2)))
        );
    }
}
