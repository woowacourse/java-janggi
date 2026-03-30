package janggi.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board() {
        this.board = initialize();
    }

    public Map<Position, Piece> initialize() {
        Map<Position, Piece> initBoard = new HashMap<>();

        initBoard.put(new Position(1, 7), new Piece(Team.CHO, PieceType.ZOL));
        initBoard.put(new Position(3, 7), new Piece(Team.CHO, PieceType.ZOL));
        initBoard.put(new Position(5, 7), new Piece(Team.CHO, PieceType.ZOL));
        initBoard.put(new Position(7, 7), new Piece(Team.CHO, PieceType.ZOL));
        initBoard.put(new Position(9, 7), new Piece(Team.CHO, PieceType.ZOL));

        initBoard.put(new Position(2, 8), new Piece(Team.CHO, PieceType.PO));
        initBoard.put(new Position(8, 8), new Piece(Team.CHO, PieceType.PO));

        initBoard.put(new Position(1, 10), new Piece(Team.CHO, PieceType.CHA));
        initBoard.put(new Position(9, 10), new Piece(Team.CHO, PieceType.CHA));

        initBoard.put(new Position(2, 10), new Piece(Team.CHO, PieceType.MA));
        initBoard.put(new Position(3, 10), new Piece(Team.CHO, PieceType.SANG));
        initBoard.put(new Position(7, 10), new Piece(Team.CHO, PieceType.MA));
        initBoard.put(new Position(8, 10), new Piece(Team.CHO, PieceType.SANG));

        initBoard.put(new Position(4, 10), new Piece(Team.CHO, PieceType.SA));
        initBoard.put(new Position(6, 10), new Piece(Team.CHO, PieceType.SA));
        initBoard.put(new Position(5, 9), new Piece(Team.CHO, PieceType.KING));

        initBoard.put(new Position(1, 4), new Piece(Team.HAN, PieceType.ZOL));
        initBoard.put(new Position(3, 4), new Piece(Team.HAN, PieceType.ZOL));
        initBoard.put(new Position(5, 4), new Piece(Team.HAN, PieceType.ZOL));
        initBoard.put(new Position(7, 4), new Piece(Team.HAN, PieceType.ZOL));
        initBoard.put(new Position(9, 4), new Piece(Team.HAN, PieceType.ZOL));

        initBoard.put(new Position(2, 3), new Piece(Team.HAN, PieceType.PO));
        initBoard.put(new Position(8, 3), new Piece(Team.HAN, PieceType.PO));

        initBoard.put(new Position(1, 1), new Piece(Team.HAN, PieceType.CHA));
        initBoard.put(new Position(9, 1), new Piece(Team.HAN, PieceType.CHA));

        initBoard.put(new Position(2, 1), new Piece(Team.HAN, PieceType.MA));
        initBoard.put(new Position(3, 1), new Piece(Team.HAN, PieceType.SANG));
        initBoard.put(new Position(7, 1), new Piece(Team.HAN, PieceType.MA));
        initBoard.put(new Position(8, 1), new Piece(Team.HAN, PieceType.SANG));

        initBoard.put(new Position(4, 1), new Piece(Team.HAN, PieceType.SA));
        initBoard.put(new Position(6, 1), new Piece(Team.HAN, PieceType.SA));
        initBoard.put(new Position(5, 2), new Piece(Team.HAN, PieceType.KING));

        return initBoard;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public List<Position> findAvailablePositions(Position position) {
        Piece piece = board.get(position);
        PieceType pieceType = piece.getPieceType();

        MoveRule moveRule = pieceType.getMoveRule();

        List<Route> routes = moveRule.findRoutes(piece.getTeam());

        Map<Position, List<Position>> routePositions = convertToPositions(position, routes);

        List<Position> availablePositions = new ArrayList<>();
        for (Map.Entry<Position, List<Position>> entry : routePositions.entrySet()) {
            Position destination = entry.getKey();
            Piece destinationPiece = board.get(destination);
            List<Position> route = entry.getValue();

            if (hasObstacleOnRoute(route)) {
                continue;
            }

            if (board.containsKey(destination)) {
                if (isDestinationIsMyTeam(destinationPiece, piece)) {
                    continue;
                }
            }
            availablePositions.add(destination);
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

    private Map<Position, List<Position>> convertToPositions(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();

        return convertToFixedRoutes(position, routes, result);
    }

    private Map<Position, List<Position>> convertToFixedRoutes(Position position, List<Route> routes,
                                                               Map<Position, List<Position>> result) {
        for (Route route : routes) {
            int currentX = position.getX();
            int currentY = position.getY();
            List<Position> routeToPositions = new ArrayList<>();
            List<Direction> directions = route.getRoutes();

            boolean isInBoard = true;
            for (Direction direction : directions) {
                currentX += direction.getX();
                currentY += direction.getY();
                if (!Position.isInsideBoundary(currentX, currentY)) {
                    isInBoard = false;
                    break;
                }
                routeToPositions.add(new Position(currentX, currentY));
            }
            if (isInBoard && !routeToPositions.isEmpty()) {
                result.put(routeToPositions.getLast(), routeToPositions);
            }
        }
        return result;
    }
}
