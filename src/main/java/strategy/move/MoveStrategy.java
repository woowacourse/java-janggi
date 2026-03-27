package strategy.move;

import domain.MovePath;
import domain.Position;
import domain.TeamColor;
import java.util.List;
import domain.Route;
import domain.Piece;

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

    public boolean canJump(List<Piece> blockingPieces);


}
