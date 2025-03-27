package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawPosition;
import janggi.domain.position.RawRoute;
import janggi.domain.routePolicy.RoutePolicyForNormal;
import java.util.List;
import java.util.Set;

public class Horse extends Piece {

    public Horse(final Position position, final Team team) {
        super(team, position, PieceType.HORSE, new RoutePolicyForNormal());
    }

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        return Set.of(
                new RawRoute(makeUpUpLeft()),
                new RawRoute(makeUpUpRight()),
                new RawRoute(makeRightRightUp()),
                new RawRoute(makeRightRightDown()),
                new RawRoute(makeDownDownRight()),
                new RawRoute(makeDownDownLeft()),
                new RawRoute(makeLeftLeftDown()),
                new RawRoute(makeLeftLeftUp())
        );
    }

    @Override
    protected Set<RawRoute> calculateAdditionalRawRoutesInPalace() {
        return Set.of();
    }

    private List<RawPosition> makeUpUpLeft() {
        return List.of(
                position.up(),
                position.up().upLeftDiagonal()
        );
    }

    private List<RawPosition> makeUpUpRight() {
        return List.of(
                position.up(),
                position.up().upRightDiagonal()
        );
    }

    private List<RawPosition> makeRightRightUp() {
        return List.of(
                position.right(),
                position.right().upRightDiagonal()
        );
    }

    private List<RawPosition> makeRightRightDown() {
        return List.of(
                position.right(),
                position.right().downRightDiagonal()
        );
    }

    private List<RawPosition> makeDownDownRight() {
        return List.of(
                position.down(),
                position.down().downRightDiagonal()
        );
    }

    private List<RawPosition> makeDownDownLeft() {
        return List.of(
                position.down(),
                position.down().downLeftDiagonal());
    }

    private List<RawPosition> makeLeftLeftDown() {
        return List.of(
                position.left(),
                position.left().downLeftDiagonal());
    }

    private List<RawPosition> makeLeftLeftUp() {
        return List.of(
                position.left(),
                position.left().upLeftDiagonal());
    }
}
