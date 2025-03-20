package janggi.board;

import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Empty;
import janggi.piece.Piece;
import janggi.view.OutputView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private static final int X_LIMIT = 9;
    private static final int Y_LIMIT = 10;

    private final Map<Position, Piece> board;

    private JanggiBoard(final Map<Position, Piece> board) {
        this.board = board;
    }

    public void printBoard() {
        OutputView outputView = new OutputView();
        outputView.printBoard(board);
    }

    public static JanggiBoard initialize() {
        Map<Position, Piece> board = BoardInitializer.initialPieces(X_LIMIT, Y_LIMIT);
        return new JanggiBoard(board);
    }

    public List<Position> filterReachableDestination(List<Route> routes, Piece piece) {
        if (piece instanceof Chariot) {
            return filterReachableDestinationChariot(routes, piece);
        }
        if (piece instanceof Cannon) {
            return filterReachableDestinationCannon(routes, piece);
        }
        return filterReachableDestinationNormal(routes, piece);
    }

    private List<Position> filterReachableDestinationChariot(List<Route> routes, Piece piece) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : routes) {
            List<Position> positions = route.getPositions();
            addValidDestination(piece, positions, reachablePositions);
        }
        return reachablePositions;
    }

    private List<Position> filterReachableDestinationCannon(List<Route> routes, Piece piece) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : routes) {
            List<Position> positions = route.getPositions();
            addValidDestinationForCannon(piece, positions, reachablePositions);
        }
        return reachablePositions;
    }

    private List<Position> filterReachableDestinationNormal(final List<Route> routes, final Piece piece) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : routes) {
            Position destination = route.getDestination();
            if (isInvalidRoute(piece, route, destination)) continue;
            reachablePositions.add(destination);
        }
        return reachablePositions;
    }

    private boolean isInvalidRoute(final Piece piece, final Route route, final Position destination) {
        if (destination.isOutOfRange(X_LIMIT, Y_LIMIT)) {
            return true;
        }
        if (checkInvalidIntermediatePositions(route)) {
            return true;
        }
        return isAlly(destination, piece);
    }

    private boolean checkInvalidIntermediatePositions(final Route route) {
        return route.getIntermediatePositions().stream()
                .anyMatch(this::isPositionHasPiece);
    }

    private void addValidDestination(final Piece piece, final List<Position> positions, final List<Position> reachablePositions) {
        for (Position position : positions) {
            if (isBoundPosition(piece, position, reachablePositions)) break;
            reachablePositions.add(position);
        }
    }

    private boolean isBoundPosition(final Piece piece, final Position position, final List<Position> reachablePositions) {
        if (position.isOutOfRange(X_LIMIT, Y_LIMIT) || isAlly(position, piece)) {
            return true;
        }
        if (isPositionHasPiece(position) && isEnemy(position, piece)) {
            reachablePositions.add(position);
            return true;
        }
        return false;
    }

    private void addValidDestinationForCannon(final Piece piece, final List<Position> positions, final List<Position> reachablePositions) {
        boolean hasJumped = false;
        for (Position position : positions) {
            if (position.isOutOfRange(X_LIMIT, Y_LIMIT) || isPositionCannon(position)) break;
            if (!hasJumped && updateJumpState(position)) {
                hasJumped = true;
                continue;
            }

            if (hasJumped && processJumpedPosition(piece, reachablePositions, position)) break;
        }
    }

    private boolean updateJumpState(Position position) {
        return isPositionHasPiece(position);
    }

    private boolean processJumpedPosition(Piece piece, List<Position> reachablePositions, Position position) {
        if (isPositionHasPiece(position)) {
            addValidDestinationIfEnemy(piece, reachablePositions, position);
            return true;
        }
        reachablePositions.add(position);
        return false;
    }

    private void addValidDestinationIfEnemy(final Piece piece, final List<Position> reachablePositions, final Position position) {
        if (isEnemy(position, piece)) {
            reachablePositions.add(position);
        }
    }

    private boolean isPositionHasPiece(Position position) {
        return !isPositionEmpty(position);
    }

    private boolean isPositionCannon(Position position) {
        return board.get(position) instanceof Cannon;
    }

    private boolean isPositionEmpty(Position position) {
        return board.get(position) instanceof Empty;
    }

    private boolean isAlly(Position position, Piece piece) {
        Piece anotherPiece = board.get(position);
        if (piece.isCho()) {
            return anotherPiece.isCho();
        }
        if (piece.isHan()) {
            return anotherPiece.isHan();
        }
        throw new IllegalStateException("[ERROR] 프로그램에 오류가 발생했습니다.");
    }

    private boolean isEnemy(Position position, Piece piece) {
        Piece anotherPiece = board.get(position);
        if (piece.isCho()) {
            return anotherPiece.isHan();
        }
        if (piece.isHan()) {
            return anotherPiece.isCho();
        }
        throw new IllegalStateException("[ERROR] 프로그램에 오류가 발생했습니다.");
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

}
