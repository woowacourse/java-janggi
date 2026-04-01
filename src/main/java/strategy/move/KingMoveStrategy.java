package strategy.move;

import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.palace.Palace;
import java.util.List;

public class KingMoveStrategy extends MoveStrategy {

    private final Palace hanPalace;
    private final Palace choPalace;

    public KingMoveStrategy() {
        this(Palace.createHanPalace(), Palace.createChoPalace());
    }

    KingMoveStrategy(Palace hanPalace, Palace choPalace) {
        this.hanPalace = hanPalace;
        this.choPalace = choPalace;
    }

    @Override
    public List<MovePath> getPaths(Piece piece) {
        return List.of();
    }

    @Override
    public List<Route> makeRoutes(Position curPos, Piece piece) {
        return palaceFor(piece.getTeamColor()).getAdjacentPositions(curPos).stream()
                .map(destination -> new Route(curPos, destination, List.of()))
                .toList();
    }

    private Palace palaceFor(TeamColor teamColor) {
        if (teamColor == TeamColor.HAN) {
            return hanPalace;
        }
        return choPalace;
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Piece pieceAtDestination, TeamColor myTeam) {
        if (!blockingPieces.isEmpty()) {
            return false;
        }

        return pieceAtDestination == null || !pieceAtDestination.isOnTeam(myTeam);
    }

}
