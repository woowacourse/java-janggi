package janggi.domain.board;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Route;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> initBoard) {
        this.board = initBoard;
    }

    public List<Position> findAvailablePositions(Position position) {
        Piece piece = board.get(position);
        List<Route> routes = piece.findRoutes();
        Map<Position, List<Position>> routePositions = convertToPositions(position, piece, routes);

        List<Position> availablePositions = new ArrayList<>();
        for (Map.Entry<Position, List<Position>> entry : routePositions.entrySet()) {
            Position destination = entry.getKey();
            List<Position> route = entry.getValue();
            if (canPieceMove(piece, destination, route)) {
                availablePositions.add(destination);
            }
        }
        validateCantMovePiece(availablePositions);
        return availablePositions;
    }


    private boolean canPieceMove(Piece piece, Position destination, List<Position> route) {
        Piece destinationPiece = board.get(destination);
        if (piece.isPo()) {
            return canPoMove(piece, destinationPiece, route);
        }
        return canGeneralPieceMove(piece, destinationPiece, route);
    }

    private boolean canPoMove(Piece piece, Piece destinationPiece, List<Position> route) {
        if (!hasOneObstacleAndNotPo(route)) {
            return false;
        }
        if (destinationPiece == null) {
            return true;
        }
        return isDestinationIsEnemy(destinationPiece, piece) && !isDestinationIsPo(destinationPiece);
    }

    private boolean canGeneralPieceMove(Piece piece, Piece destinationPiece, List<Position> route) {
        if (hasObstacleOnRoute(route)) {
            return false;
        }
        if (destinationPiece == null) {
            return true;
        }
        return isDestinationIsEnemy(destinationPiece, piece);
    }

    private boolean isDestinationIsPo(Piece destinationPiece) {
        return destinationPiece.getPieceType() == PieceType.PO;
    }

    private boolean isDestinationIsEnemy(Piece destinationPiece, Piece piece) {
        return destinationPiece.getTeam() != piece.getTeam();
    }

    private boolean hasObstacleOnRoute(List<Position> route) {
        Position targetPosition = route.getLast();
        return route.stream()
                .filter(position -> position != targetPosition)
                .anyMatch(board::containsKey);
    }

    private Map<Position, List<Position>> convertToPositions(Position position, Piece piece, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();

        if (piece.isCha() || piece.isPo()) {
            return convertToContinuousRoutes(position, routes, result);
        }

        return convertToFixedRoutes(position, routes, result);
    }

    private Map<Position, List<Position>> convertToFixedRoutes(Position position, List<Route> routes,
                                                               Map<Position, List<Position>> result) {
        for (Route route : routes) {
            addValidFixedRoute(position, result, route);
        }
        return result;
    }

    private static void addValidFixedRoute(Position position, Map<Position, List<Position>> result, Route route) {
        int currentColumn = position.getRow();
        int currentRow = position.getColumn();
        List<Position> routeToPositions = new ArrayList<>();

        for (Direction direction : route.getRoutes()) {
            currentColumn += direction.getColumn();
            currentRow += direction.getRow();
            if (!Position.isInsideBoundary(currentColumn, currentRow)) {
                return;
            }
            routeToPositions.add(new Position(currentColumn, currentRow));
        }
        if (!routeToPositions.isEmpty()) {
            result.put(routeToPositions.getLast(), routeToPositions);
        }
    }

    private Map<Position, List<Position>> convertToContinuousRoutes(Position position, List<Route> routes,
                                                                    Map<Position, List<Position>> result) {
        for (Route route : routes) {
            addValidContinuousPosition(position, result, route);
        }
        return result;
    }

    private static void addValidContinuousPosition(Position position, Map<Position, List<Position>> result,
                                                   Route route) {
        int currentX = position.getRow();
        int currentY = position.getColumn();
        List<Direction> directions = route.getRoutes();

        for (Direction direction : directions) {
            List<Position> routeToPositions = new ArrayList<>();
            currentX += direction.getColumn();
            currentY += direction.getRow();
            while (Position.isInsideBoundary(currentX, currentY)) {
                Position movePosition = new Position(currentX, currentY);
                routeToPositions.add(movePosition);
                result.put(movePosition, new ArrayList<>(routeToPositions));
                currentX += direction.getColumn();
                currentY += direction.getRow();
            }
        }
    }

    private boolean hasOneObstacleAndNotPo(List<Position> route) {
        int count = 0;
        List<Piece> obstacles = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            if (board.containsKey(route.get(i))) {
                count++;
                obstacles.add(board.get(route.get(i)));
            }
        }
        return count == 1 && obstacles.getFirst().getPieceType() != PieceType.PO;
    }

    public void movePiece(Position movePiecePosition, Position destination) {
        Piece piece = board.get(movePiecePosition);
        board.remove(movePiecePosition);
        board.put(destination, piece);
    }

    public Piece getPiece(Position position) {
        return board.get(position);
    }

    public Map<Position, Piece> getBoard() {
        return board;
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

        if (!hasPosition) {
            throw new IllegalArgumentException("[ERROR] 이동 가능한 좌표 중에서 선택하세요.");
        }
    }

    private static void validateCantMovePiece(List<Position> availablePositions) {
        if (availablePositions.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없는 좌표입니다.");
        }
    }

}
