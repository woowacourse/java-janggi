package strategy.move;

import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.palace.PalaceGeometry;
import java.util.List;

public class GuardMoveStrategy extends MoveStrategy {

    private final PalaceGeometry palaceGeometry;

    public GuardMoveStrategy() {
        this(new PalaceGeometry());
    }

    GuardMoveStrategy(PalaceGeometry palaceGeometry) {
        this.palaceGeometry = palaceGeometry;
    }

    @Override
    public List<MovePath> getPaths(Piece piece) {
        return List.of();
    }

    @Override
    public List<Route> makeRoutes(Position curPos, Piece piece) {
        return palaceGeometry.adjacentPositionsInsideMyPalace(curPos, piece.getTeamColor()).stream()
                .map(destination -> new Route(curPos, destination, List.of()))
                .toList();
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Piece pieceAtDestination, TeamColor myTeam) {
        if (!blockingPieces.isEmpty()) {
            return false;
        }

        return pieceAtDestination == null || !pieceAtDestination.isOnTeam(myTeam);
    }

}
