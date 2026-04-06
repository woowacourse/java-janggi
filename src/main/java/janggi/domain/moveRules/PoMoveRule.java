package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Team;
import janggi.domain.board.Board;
import java.util.ArrayList;
import java.util.List;

public class PoMoveRule implements MoveRule {

    @Override
    public List<Route> findRoutes(Team team) {
        Route route1 = new Route(List.of(Direction.NORTH));
        Route route2 = new Route(List.of(Direction.EAST));
        Route route3 = new Route(List.of(Direction.SOUTH));
        Route route4 = new Route(List.of(Direction.WEST));
        return List.of(route1, route2, route3, route4);
    }

    @Override
    public List<Position> calculateAvailablePositions(Position position, Team team, Board board) {
        List<Position> availablePositions = new ArrayList<>();
        List<Direction> directions = List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
        for (Direction direction : directions) {
            Position bridge = findBridge(position, direction, board);
            if (bridge != null) {
                collectPositions(bridge, direction, team, board, availablePositions);
            }
        }
        return availablePositions;
    }

    private Position findBridge(Position currentPosition, Direction direction, Board board) {
        if (!currentPosition.canMoveTo(direction)) {
            return null;
        }
        Position next = currentPosition.move(direction);
        Piece piece = board.getPiece(next);

        if (piece == null) {
            return findBridge(next, direction, board);
        }
        if (piece.isPo()) {
            return null;
        }
        return next;
    }

    private void collectPositions(Position currentPosition, Direction direction, Team team, Board board,
                                  List<Position> result) {
        if (!currentPosition.canMoveTo(direction)) {
            return;
        }
        Position next = currentPosition.move(direction);
        Piece piece = board.getPiece(next);

        addIfValid(piece, next, team, result);

        if (piece == null) {
            collectPositions(next, direction, team, board, result);
        }
    }

    private void addIfValid(Piece piece, Position next, Team team, List<Position> result) {
        if (piece == null) {
            result.add(next);
            return;
        }
        if (!piece.isAlly(team) && !piece.isPo()) {
            result.add(next);
        }
    }
}
