package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawPosition;
import janggi.domain.position.RawRoute;
import janggi.domain.routePolicy.RoutePolicyForNormal;
import java.util.List;
import java.util.Set;

public class Elephant extends Piece {

    public Elephant(final Position position, final Team team) {
        super(team, position, PieceType.ELEPHANT, new RoutePolicyForNormal());
    }

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        return Set.of(
                new RawRoute(makeUpUpUpLeftLeft()),
                new RawRoute(makeUpUpUpRightRight()),
                new RawRoute(makeUpUpRightRightRight()),
                new RawRoute(makeDownDownRightRightRight()),
                new RawRoute(makeDownDownDownRightRight()),
                new RawRoute(makeDownDownDownLeftLeft()),
                new RawRoute(makeDownDownLeftLeftLeft()),
                new RawRoute(makeUpUpLeftLeftLeft())
        );
    }

    @Override
    protected Set<RawRoute> calculateAdditionalRawRoutesInPalace() {
        return Set.of();
    }

    private List<RawPosition> makeUpUpUpLeftLeft() {
        return List.of(
                new RawPosition(position.x(), position.y() + 1),
                new RawPosition(position.x() - 1, position.y() + 2),
                new RawPosition(position.x() - 2, position.y() + 3)
        );
    }

    private List<RawPosition> makeUpUpUpRightRight() {
        return List.of(
                new RawPosition(position.x(), position.y() + 1),
                new RawPosition(position.x() + 1, position.y() + 2),
                new RawPosition(position.x() + 2, position.y() + 3)
        );
    }

    private List<RawPosition> makeUpUpRightRightRight() {
        return List.of(
                new RawPosition(position.x() + 1, position.y()),
                new RawPosition(position.x() + 2, position.y() + 1),
                new RawPosition(position.x() + 3, position.y() + 2)
        );
    }

    private List<RawPosition> makeDownDownRightRightRight() {
        return List.of(
                new RawPosition(position.x() + 1, position.y()),
                new RawPosition(position.x() + 2, position.y() - 1),
                new RawPosition(position.x() + 3, position.y() - 2)
        );
    }

    private List<RawPosition> makeDownDownDownRightRight() {
        return List.of(
                new RawPosition(position.x(), position.y() - 1),
                new RawPosition(position.x() + 1, position.y() - 2),
                new RawPosition(position.x() + 2, position.y() - 3)
        );
    }

    private List<RawPosition> makeDownDownDownLeftLeft() {
        return List.of(
                new RawPosition(position.x(), position.y() - 1),
                new RawPosition(position.x() - 1, position.y() - 2),
                new RawPosition(position.x() - 2, position.y() - 3)
        );
    }

    private List<RawPosition> makeDownDownLeftLeftLeft() {
        return List.of(
                new RawPosition(position.x() - 1, position.y()),
                new RawPosition(position.x() - 2, position.y() - 1),
                new RawPosition(position.x() - 3, position.y() - 2)
        );
    }

    private List<RawPosition> makeUpUpLeftLeftLeft() {
        return List.of(
                new RawPosition(position.x() - 1, position.y()),
                new RawPosition(position.x() - 2, position.y() + 1),
                new RawPosition(position.x() - 3, position.y() + 2)
        );
    }
}
