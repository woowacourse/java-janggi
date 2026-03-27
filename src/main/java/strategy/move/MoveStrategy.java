package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MoveStrategy {

    List<MovePath> getPaths(TeamColor teamColor);

    default List<Route> makeRoutes(Position curPos, TeamColor teamColor) {
        List<Route> validRoutes = new ArrayList<>();
        List<MovePath> paths = getPaths(teamColor);

        for (MovePath path : paths) {
            List<Direction> steps = path.steps();
            Position currentPos = curPos;
            List<Position> intermediates = new ArrayList<>();


            for (int i = 0; i < steps.size(); i++) {
                currentPos = currentPos.next(steps.get(i));

                if (i < steps.size() - 1) {
                    intermediates.add(currentPos);
                }
            }
            validRoutes.add(new Route(curPos, currentPos, intermediates));
        }

        return validRoutes;
    }

    default boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece, TeamColor myTeam) {
        if (!blockingPieces.isEmpty()) {
            return false;
        }

        return destinationPiece.isEmpty() || destinationPiece.get().getTeamColor() != myTeam;
    }

}
