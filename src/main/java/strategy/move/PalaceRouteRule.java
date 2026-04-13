package strategy.move;

import domain.board.Position;
import domain.board.Route;
import domain.piece.TeamColor;
import java.util.List;

@FunctionalInterface
interface PalaceRouteRule {
    List<Route> generate(Position position, TeamColor teamColor);
}
