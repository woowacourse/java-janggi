package janggi.board;

import janggi.piece.Cannon;
import janggi.piece.Empty;
import janggi.piece.King;
import janggi.piece.Piece;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private static final int X_LIMIT = 9;
    private static final int Y_LIMIT = 10;

    private final Map<Position, Piece> board;

    private JanggiBoard(Map<Position, Piece> board) {
        this.board = board;
    }

    public static JanggiBoard initialize() {
        Map<Position, Piece> board = BoardInitializer.initialPieces(X_LIMIT, Y_LIMIT);
        return new JanggiBoard(board);
    }

    public List<Position> computeReachableDestination(final Position position) {
        Piece piece = board.get(position);
        validatePositionHasPiece(piece);

        List<Route> candidatesRoutes = piece.computeCandidatePositions(position);
        List<Position> reachableDestinations = piece.filterReachableDestinations(candidatesRoutes, this);

        validateReachableDestinations(reachableDestinations);
        return reachableDestinations;
    }

    public Piece moveOrCatchPiece(final Position selectedPiecePosition, final Position destination,
                                  final List<Position> reachableDestinations) {
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

    public boolean isPositionCannon(final Position position) {
        return findPieceBy(position) instanceof Cannon;
    }

    public boolean isOutOfRange(final Position position) {
        return position.isOutOfRange(X_LIMIT, Y_LIMIT);
    }

    public boolean isPositionHasPiece(final Position position) {
        return !isPositionEmpty(position);
    }

    public boolean checkInvalidIntermediatePositions(final Route route) {
        return route.getIntermediatePositions().stream()
                .anyMatch(this::isPositionHasPiece);
    }

    public Piece findPieceBy(final Position position) {
        return board.get(position);
    }

    private boolean isPositionEmpty(final Position position) {
        return board.get(position) instanceof Empty;
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

    private void validateExistSelectedDestination(final Position destination,
                                                  final List<Position> reachableDestinations) {
        if (!reachableDestinations.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 선택한 목적지로 이동할 수 없습니다.");
        }
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

}
