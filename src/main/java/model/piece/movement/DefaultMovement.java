package model.piece.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.Position;
import model.Team;
import model.board.BoardSearcher;
import model.piece.Piece;

public class DefaultMovement implements Movement {

    private boolean normalMove = true;
    private final List<Movement> movements = new ArrayList<>();

    public void normalMoveDisable() {
        normalMove = false;
    }

    public void addMovement(Movement movement) {
        movements.add(movement);
    }

    @Override
    public Piece.Route findMovableRoute(BoardSearcher boardSearcher, List<Piece.Route> routes, Team team,
        Position position, Position difference) {
        Position target = position.move(difference);
        Optional<Piece.Route> movableRoute = movements.stream()
            .map(movement -> movement.findMovableRoute(boardSearcher, routes, team, position, difference))
            .filter(Objects::nonNull)
            .findFirst();
        return movableRoute.orElseGet(() -> findMovableRouteInNormalRoute(routes, team, position, target));
    }

    private Piece.Route findMovableRouteInNormalRoute(List<Piece.Route> routes, Team team, Position position,
        Position target) {
        movements.forEach(movement -> movement.validateNormalRoute(team, target));
        for (var route : routes) {
            Position routeSum = route.sum();
            Position expected = position.move(routeSum);
            if (target.equals(expected)) {
                return route;
            }
        }
        throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
    }

    @Override
    public void validateRoute(BoardSearcher boardSearcher, Piece.Route route, Position position, Position difference) {
        movements.forEach(movement -> movement.validateRoute(boardSearcher, route, position, difference));
        Movement.super.validateRoute(boardSearcher, route, position, difference);
    }
}
