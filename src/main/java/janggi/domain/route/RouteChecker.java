package janggi.domain.route;

import janggi.domain.board.Board;
import janggi.domain.common.Position;
import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteChecker {
    private final RouteConverter routeConverter = new RouteConverter();

    public List<Position> findAvailablePositions(Board board, Position position) {
        Piece piece = board.pieceAt(position);

        Map<Position, List<Position>> routePositions = routeConverter.convertToPosition(board, position);

        List<Position> availablePositions = calculateAvailablePositions(board, piece, routePositions);

        if (availablePositions.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없는 좌표입니다.");
        }
        return availablePositions;
    }

    public void validateDestination(Board board, Position movePiecePosition, Position destination) {
        List<Position> availablePositions = findAvailablePositions(board, movePiecePosition);
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

    private List<Position> calculateAvailablePositions(Board board, Piece piece,
                                                       Map<Position, List<Position>> routePositions) {
        List<Position> result = new ArrayList<>();
        for (Map.Entry<Position, List<Position>> entry : routePositions.entrySet()) {
            Position destination = entry.getKey();
            List<Position> route = entry.getValue();

            if (canMove(board, piece, route, destination)) {
                result.add(destination);
            }
        }
        return result;
    }

    private boolean canMove(Board board, Piece movePiece, List<Position> route, Position destination) {
        if (movePiece.isPo()) {
            return hasOneObstacleAndNotPo(board, route)
                    && !isDestinationIsMyTeam(board, destination, movePiece)
                    && !isDestinationIsPo(board, destination);
        }
        return !hasObstacleOnRoute(board, route) && !isDestinationIsMyTeam(board, destination, movePiece);
    }

    private boolean isDestinationIsMyTeam(Board board, Position destination, Piece piece) {
        Piece destinationPiece = board.pieceAt(destination);
        if (board.hasPiece(destination)) {
            return piece.isSameTeam(destinationPiece);
        }
        return false;
    }

    private boolean hasObstacleOnRoute(Board board, List<Position> route) {
        for (int i = 0; i < route.size() - 1; i++) {
            if (board.hasPiece(route.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isDestinationIsPo(Board board, Position destination) {
        Piece destinationPiece = board.pieceAt(destination);
        if (board.hasPiece(destination)) {
            return destinationPiece.isPo();
        }
        return false;
    }

    private boolean hasOneObstacleAndNotPo(Board board, List<Position> route) {
        int count = 0;
        List<Piece> obstacles = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            if (board.hasPiece(route.get(i))) {
                count += 1;
                obstacles.add(board.pieceAt(route.get(i)));
            }
        }
        return count == 1 && !obstacles.getFirst().isPo();
    }
}
