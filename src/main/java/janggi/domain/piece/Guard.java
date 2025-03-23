package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawPosition;
import janggi.domain.position.RawRoute;
import java.util.List;
import java.util.Set;

public class Guard extends Piece {

    public Guard(final Position position, final Team team) {
        super(position, team);
        this.pieceType = PieceType.GUARD;
    }

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        return Set.of(
                new RawRoute(List.of(new RawPosition(position.x() + 1, position.y()))),
                new RawRoute(List.of(new RawPosition(position.x() - 1, position.y()))),
                new RawRoute(List.of(new RawPosition(position.x(), position.y() + 1))),
                new RawRoute(List.of(new RawPosition(position.x(), position.y() - 1)))
        );
    }
}
