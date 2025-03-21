package janggi.board;

import janggi.piece.*;

import java.util.*;

public class JanggiBoard {

    private static final int X_LIMIT = 9;
    private static final int Y_LIMIT = 10;

    private final Map<Position, Piece> board;

    public JanggiBoard(Map<Position, Piece> board) { this.board = new HashMap<>(board); }

    public static JanggiBoard initializeWithPieces() {
        Map<Position, Piece> board = BoardInitializer.initialPieces(X_LIMIT, Y_LIMIT);
        return new JanggiBoard(board);
    }

    public List<Position> computeReachableDestination(final Position position) {
        Piece piece = board.get(position);
        validatePositionHasPiece(piece);

        List<Route> candidatesRoutes = piece.computeCandidatePositions(position);
        List<Position> reachableDestinations = filterReachableDestinations(piece, candidatesRoutes);

        validateReachableDestinations(reachableDestinations);
        return reachableDestinations;
    }

    public Piece moveOrCatchPiece(final Position selectedPiecePosition, final Position destination, final List<Position> reachableDestinations) {
        validateExistSelectedDestination(destination, reachableDestinations);
        Piece seletedPiece = board.get(selectedPiecePosition);
        board.put(selectedPiecePosition, new Empty());

        Piece destinationPiece = board.get(destination);
        board.put(destination, seletedPiece);

        return destinationPiece;
    }

    public boolean checkGameIsOver(final Piece catchedPiece) {
        return catchedPiece instanceof King;
    }

    private List<Position> filterReachableDestinations(final Piece piece, final List<Route> candidatesRoutes) {
        if (piece instanceof Chariot) {
            return filterReachableDestinationsChariot(candidatesRoutes, piece);
        }
        if (piece instanceof Cannon) {
            return filterReachableDestinationsCannon(candidatesRoutes, piece);
        }
        return filterReachableDestinationsNormal(candidatesRoutes, piece);
    }

    private List<Position> filterReachableDestinationsChariot(final List<Route> routes, final Piece piece) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : routes) {
            List<Position> positions = route.getPositions();
            addValidDestination(piece, positions, reachablePositions);
        }
        return reachablePositions;
    }

    private List<Position> filterReachableDestinationsCannon(final List<Route> routes, final Piece piece) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : routes) {
            List<Position> positions = route.getPositions();
            addValidDestinationForCannon(piece, positions, reachablePositions);
        }
        return reachablePositions;
    }

    private List<Position> filterReachableDestinationsNormal(final List<Route> routes, final Piece piece) {
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

    private boolean updateJumpState(final Position position) {
        return isPositionHasPiece(position);
    }

    private boolean processJumpedPosition(final Piece piece, final List<Position> reachablePositions, final Position position) {
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

    private boolean isPositionHasPiece(final Position position) {
        return !isPositionEmpty(position);
    }

    private boolean isPositionCannon(final Position position) {
        return board.get(position) instanceof Cannon;
    }

    private boolean isPositionEmpty(final Position position) {
        return board.get(position) instanceof Empty;
    }

    private boolean isAlly(final Position position, final Piece piece) {
        Piece anotherPiece = board.get(position);
        if (piece.isCho()) {
            return anotherPiece.isCho();
        }
        if (piece.isHan()) {
            return anotherPiece.isHan();
        }
        throw new IllegalStateException("[ERROR] 프로그램에 오류가 발생했습니다.");
    }

    private boolean isEnemy(final Position position, final Piece piece) {
        Piece anotherPiece = board.get(position);
        if (piece.isCho()) {
            return anotherPiece.isHan();
        }
        if (piece.isHan()) {
            return anotherPiece.isCho();
        }
        throw new IllegalStateException("[ERROR] 프로그램에 오류가 발생했습니다.");
    }

    private void validatePositionHasPiece(final Piece piece) {
        if (piece instanceof Empty) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 움직일 수 있는 기물이 없습니다.");
        }
    }

    private void validateReachableDestinations(final List<Position> reachableDestinations) {
        if (reachableDestinations.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동 가능한 목적지가 존재하지 않습니다.");
        }
    }

    private void validateExistSelectedDestination(final Position destination, final List<Position> reachableDestinations) {
        if (!reachableDestinations.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 선택한 목적지로 이동할 수 없습니다.");
        }
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

}
