package janggi.domain.piece;

import janggi.domain.RawRoute;
import janggi.domain.Team;
import java.util.List;
import java.util.Set;

public class Elephant extends Piece {

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        return Set.of(
                new RawRoute(makeRawPositions1()),
                new RawRoute(makeRawPositions2()),
                new RawRoute(makeRawPositions3()),
                new RawRoute(makeRawPositions4()),
                new RawRoute(makeRawPositions5()),
                new RawRoute(makeRawPositions6()),
                new RawRoute(makeRawPositions7()),
                new RawRoute(makeRawPositions8())
        );
    }

    public Elephant(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    private List<RawPosition> makeRawPositions1() {
        return List.of(
                new RawPosition(position.x(), position.y() + 1),
                new RawPosition(position.x() - 1, position.y() + 2),
                new RawPosition(position.x() - 2, position.y() + 3)
        );
    }

    private List<RawPosition> makeRawPositions2() {
        return List.of(
                new RawPosition(position.x(), position.y() + 1),
                new RawPosition(position.x() + 1, position.y() + 2),
                new RawPosition(position.x() + 2, position.y() + 3)
        );
    }

    private List<RawPosition> makeRawPositions3() {
        return List.of(
                new RawPosition(position.x() + 1, position.y()),
                new RawPosition(position.x() + 2, position.y() + 1),
                new RawPosition(position.x() + 3, position.y() + 2)
        );
    }

    private List<RawPosition> makeRawPositions4() {
        return List.of(
                new RawPosition(position.x() + 1, position.y()),
                new RawPosition(position.x() + 2, position.y() - 1),
                new RawPosition(position.x() + 3, position.y() - 2)
        );
    }

    private List<RawPosition> makeRawPositions5() {
        return List.of(
                new RawPosition(position.x(), position.y() - 1),
                new RawPosition(position.x() + 1, position.y() - 2),
                new RawPosition(position.x() + 2, position.y() - 3)
        );
    }

    private List<RawPosition> makeRawPositions6() {
        return List.of(
                new RawPosition(position.x(), position.y() - 1),
                new RawPosition(position.x() - 1, position.y() - 2),
                new RawPosition(position.x() - 2, position.y() - 3)
        );
    }

    private List<RawPosition> makeRawPositions7() {
        return List.of(
                new RawPosition(position.x() - 1, position.y()),
                new RawPosition(position.x() - 2, position.y() - 1),
                new RawPosition(position.x() - 3, position.y() - 2)
        );
    }

    private List<RawPosition> makeRawPositions8() {
        return List.of(
                new RawPosition(position.x() - 1, position.y()),
                new RawPosition(position.x() - 2, position.y() + 1),
                new RawPosition(position.x() - 3, position.y() + 2)
        );
    }
}
