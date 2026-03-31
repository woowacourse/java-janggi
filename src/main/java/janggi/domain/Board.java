package janggi.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board = new HashMap<>();

    public void initialize() {
        for(Team team:Team.values()) {
            for(PieceType pieceType:PieceType.values()) {
                pieceType.placeOnBoard(this, team);
            }
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

        List<Position> availablePositions = new ArrayList<>();
        for (Map.Entry<Position, List<Position>> entry : routePositions.entrySet()) {
            Position destination = entry.getKey();
            Piece destinationPiece = board.get(destination);
            List<Position> route = entry.getValue();

            if(piece.getPieceType() == PieceType.PO) {
                if(!hasOneObstacleAndNotPo(route)) {
                    continue;
                }

                if(board.containsKey(destination)) {
                    if(isDestinationIsMyTeam(destinationPiece, piece) || isDestinationIsPo(destinationPiece)) {
                        continue;
                    }
                }
                availablePositions.add(destination);
            } else {
                if(hasObstacleOnRoute(route)) {
                    continue;
                }
                if(board.containsKey(destination)) {
                    if(isDestinationIsMyTeam(destinationPiece, piece)) {
                        continue;
                    }
                }
                availablePositions.add(destination);
            }
        }

        if(availablePositions.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없는 좌표입니다.");
        }
        return availablePositions;
    }

    private boolean isDestinationIsMyTeam(Piece destinationPiece, Piece piece) {
        return destinationPiece.getTeam() == piece.getTeam();
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
        if(piece.isCha()|| piece.isPo()) {
            return convertToContinuousRoutes(position, routes);
        }
        return convertToFixedRoutes(position, routes);
    }

    private Map<Position, List<Position>> convertToFixedRoutes(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for(Route route:routes) {
            List<Position> positionRoute = route.applyDirections(position);
            if(positionRoute.isEmpty()) {
                continue;
            }
            result.put(positionRoute.getLast(), positionRoute);
        }
        return result;
    }

    private Map<Position, List<Position>> convertToContinuousRoutes(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for(Route route: routes) {
            route.applyContinuousDirections(position, result);
        }
        return result;
    }

    private boolean isDestinationIsPo(Piece destinationPiece) {
        return destinationPiece.getPieceType() == PieceType.PO;
    }

    private boolean hasOneObstacleAndNotPo(List<Position> route) {
        int count = 0;
        List<Piece> obstacles = new ArrayList<>();
        for(int i = 0; i < route.size() -1; i++) {
            if(board.containsKey(route.get(i))) {
                count+=1;
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

    public void validateDestination(Position movePiecePosition, Position destination) {
        List<Position> availablePositions = findAvailablePositions(movePiecePosition);
        boolean hasPosition = false;
        for(Position position:availablePositions) {
            if (position == destination) {
                hasPosition = true;
                break;
            }
        }
        if(hasPosition == false) {
            throw new IllegalArgumentException("[ERROR] 이동 가능한 좌표 중에서 선택하세요.");
        }
    }
}
