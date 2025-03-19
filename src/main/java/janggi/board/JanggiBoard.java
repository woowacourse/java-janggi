package janggi.board;

import janggi.piece.Cannon;
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

    public static JanggiBoard initialize() {
        Map<Position, Piece> board = BoardInitializer.initialPieces(X_LIMIT, Y_LIMIT);
        return new JanggiBoard(board);
    }

    public List<Position> filterReachablePosition(List<Route> routes, Piece piece) {

        List<Position> reachablePositions = new ArrayList<>();

        for (Route route : routes) {
            Position destination = route.getDestination();
            if (destination.isOutOfRange(X_LIMIT, Y_LIMIT)){
                continue;
            }

            List<Position> positions = route.getPositions();

            if (positions.size() == 1) {
                if (isPositionEmpty(destination) || isNotSameSide(destination, piece)) {
                    reachablePositions.add(destination);
                }
            }

            for (int i = 0; i < positions.size() - 1; i++) {
                Position position = positions.get(i);
                if (isPositionEmpty(position) && (isPositionEmpty(destination) || isNotSameSide(destination, piece))) {
                    reachablePositions.add(destination);
                }
            }
        }
        return reachablePositions;
    }

    public List<Position> filterReachablePositionChariot(List<Route> routes, Piece piece) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : routes) {
            List<Position> positions = route.getPositions();
            for (Position position : positions) {
                if (position.isOutOfRange(X_LIMIT, Y_LIMIT) || isSameSide(position, piece)) {
                    break;
                }
                if (isPositionNotEmpty(position) && isNotSameSide(position, piece)) {
                    reachablePositions.add(position);
                    break;
                }
                reachablePositions.add(position);
            }
        }
        return reachablePositions;
    }

    public List<Position> filterReachablePositionCannon(List<Route> routes, Piece piece) {
        List<Position> reachablePositions = new ArrayList<>();
        for (Route route : routes) {
            List<Position> positions = route.getPositions();
            int count = 0;
            for (Position position : positions) {
                if (count > 1 || position.isOutOfRange(X_LIMIT, Y_LIMIT) || isPositionCannon(position)) {
                    break;
                }
                if (count == 1) {
                    if (isPositionEmpty(position)) {
                        reachablePositions.add(position);
                        continue;
                    }
                    if (isNotSameSide(position, piece)) {
                        reachablePositions.add(position);
                        break;
                    }
                    break;
                }
                if (isPositionNotEmpty(position) && count == 0) count++;
            }
        }
        return reachablePositions;
    }

    public void printBoard() {
        OutputView outputView = new OutputView();
        outputView.printBoard(board);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    private boolean isPositionCannon(Position position) {
        return board.get(position) instanceof Cannon;
    }

    private boolean isPositionNotEmpty(Position position) {
        return !isPositionEmpty(position);
    }

    private boolean isPositionEmpty(Position position) {
        return board.get(position) instanceof Empty;
    }

    private boolean isSameSide(Position position, Piece piece) {
        Piece anotherPiece = board.get(position); // EMPTY 가능 -> NONE
        if (piece.isCho()) {
            return anotherPiece.isCho();
        }
        return anotherPiece.isHan();
    }

    private boolean isNotSameSide(Position position, Piece piece) {
        Piece anotherPiece = board.get(position); // EMPTY 가능 -> NONE
        if (piece.isCho()) {
            return anotherPiece.isHan();
        }
        return anotherPiece.isCho();
    }

}
