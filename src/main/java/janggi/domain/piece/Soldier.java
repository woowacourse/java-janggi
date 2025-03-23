package janggi.domain.piece;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawPosition;
import janggi.domain.position.RawRoute;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Soldier extends Piece {

    public static final int BLUE_DIRECTION = 1;
    public static final int RED_DIRECTION = -BLUE_DIRECTION;
    private static final Map<Team, Integer> DIRECTION = Map.of(RED, RED_DIRECTION, BLUE, BLUE_DIRECTION);

    public Soldier(final Position position, final Team team) {
        super(position, team);
        this.pieceType = PieceType.SOLIDER;
    }

    @Override
    protected Set<RawRoute> calculateRawRoutes() {
        return Set.of(
                new RawRoute(List.of(new RawPosition(position.x() + BLUE_DIRECTION, position.y()))),
                new RawRoute(List.of(new RawPosition(position.x() - BLUE_DIRECTION, position.y()))),
                new RawRoute(List.of(new RawPosition(position.x(), position.y() + DIRECTION.get(team))))
        );
    }
}
