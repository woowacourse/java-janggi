package janggi.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board = new HashMap<>();

    public void initializeByFormation(BoardFormation boardFormation, Team team) {
        for (PieceType pieceType : PieceType.values()) {
            pieceType.changeXPositionsByFormation(boardFormation);
            pieceType.placeOnBoard(this, team);
        }
    }

    public void place(Position position, Piece piece) {
        board.put(position, piece);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public List<Position> findAvailablePositions(Position position) {
        Piece piece = board.get(position);

        List<Route> routes = piece.findRoutes();

        Map<Position, List<Position>> routePositions = convertToPositions(position, piece, routes);

        List<Position> availablePositions = calculateAvailablePositions(piece, routePositions);

        if (availablePositions.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없는 좌표입니다.");
        }
        return availablePositions;
    }

    public void movePiece(Position movePiecePosition, Position destination) {
        Piece piece = board.get(movePiecePosition);
        board.remove(movePiecePosition);
        board.put(destination, piece);
    }

    public void validateDestination(Position movePiecePosition, Position destination) {
        List<Position> availablePositions = findAvailablePositions(movePiecePosition);
        boolean hasPosition = false;
        for (Position position : availablePositions) {
            if (position.equals(destination)) {
                hasPosition = true;
                break;
            }
        }
        if (hasPosition == false) {
            throw new IllegalArgumentException("[ERROR] 이동 가능한 좌표 중에서 선택하세요.");
        }
    }

    private boolean isDestinationIsMyTeam(Position destination, Piece piece) {
        Piece destinationPiece = board.get(destination);
        if (board.containsKey(destination)) {
            return piece.isSameTeam(destinationPiece);
        }
        return false;
    }

    private boolean hasObstacleOnRoute(List<Position> route) {
        for (int i = 0; i < route.size() - 1; i++) {
            if (board.containsKey(route.get(i))) {
                return true;
            }
        }
        return false;
    }

    private Map<Position, List<Position>> convertToPositions(Position position, Piece piece, List<Route> routes) {
        if (piece.isCha() || piece.isPo()) {
            return convertToContinuousRoutes(position, routes);
        }
        return convertToFixedRoutes(position, routes);
    }

    private Map<Position, List<Position>> convertToFixedRoutes(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for (Route route : routes) {
            List<Position> positionRoute = route.applyDirections(position);
            if (positionRoute.isEmpty()) {
                continue;
            }
            result.put(positionRoute.getLast(), positionRoute);
        }
        return result;
    }

    private Map<Position, List<Position>> convertToContinuousRoutes(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for (Route route : routes) {
            route.applyContinuousDirections(position, result);
        }
        return result;
    }

    private boolean isDestinationIsPo(Position destination) {
        Piece destinationPiece = board.get(destination);
        if (board.containsKey(destination)) {
            return destinationPiece.isPo();
        }
        return false;
    }

    private boolean hasOneObstacleAndNotPo(List<Position> route) {
        int count = 0;
        List<Piece> obstacles = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            if (board.containsKey(route.get(i))) {
                count += 1;
                obstacles.add(board.get(route.get(i)));
            }
        }
        return count == 1 && !obstacles.getFirst().isPo();
    }

    private List<Position> calculateAvailablePositions(Piece piece, Map<Position, List<Position>> routePositions) {
        List<Position> result = new ArrayList<>();
        for (Map.Entry<Position, List<Position>> entry : routePositions.entrySet()) {
            Position destination = entry.getKey();
            List<Position> route = entry.getValue();

            if (canMove(piece, route, destination)) {
                result.add(destination);
            }
        }
        return result;
    }

    private boolean canMove(Piece movePiece, List<Position> route, Position destination) {
        if (movePiece.isPo()) {
            return hasOneObstacleAndNotPo(route)
                    && !isDestinationIsMyTeam(destination, movePiece)
                    && !isDestinationIsPo(destination);
        }
        return !hasObstacleOnRoute(route) && !isDestinationIsMyTeam(destination, movePiece);
    }
}
