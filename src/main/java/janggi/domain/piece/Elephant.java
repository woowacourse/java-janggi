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
                position.up(),
                position.up().upLeftDiagonal(),
                position.up().upLeftDiagonal().upLeftDiagonal()
        );
    }

    private List<RawPosition> makeUpUpUpRightRight() {
        return List.of(
                position.up(),
                position.up().upRightDiagonal(),
                position.up().upRightDiagonal().upRightDiagonal()
        );
    }

    private List<RawPosition> makeUpUpRightRightRight() {
        return List.of(
                position.right(),
                position.right().upRightDiagonal(),
                position.right().upRightDiagonal().upRightDiagonal()
        );
    }

    private List<RawPosition> makeDownDownRightRightRight() {
        return List.of(
                position.right(),
                position.right().downRightDiagonal(),
                position.right().downRightDiagonal().downRightDiagonal()
        );
    }

    private List<RawPosition> makeDownDownDownRightRight() {
        return List.of(
                position.down(),
                position.down().downRightDiagonal(),
                position.down().downRightDiagonal().downRightDiagonal()
        );
    }

    private List<RawPosition> makeDownDownDownLeftLeft() {
        return List.of(
                position.down(),
                position.down().downLeftDiagonal(),
                position.down().downLeftDiagonal().downLeftDiagonal()
        );
    }

    private List<RawPosition> makeDownDownLeftLeftLeft() {
        return List.of(
                position.left(),
                position.left().downLeftDiagonal(),
                position.left().downLeftDiagonal().downLeftDiagonal()
        );
    }

    private List<RawPosition> makeUpUpLeftLeftLeft() {
        return List.of(
                position.left(),
                position.left().upLeftDiagonal(),
                position.left().upLeftDiagonal().upLeftDiagonal()
        );
    }
}
