package janggi.board;

import janggi.move.Route;
import janggi.piece.Empty;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private static final int X_LIMIT = 9;
    private static final int Y_LIMIT = 10;

    private final Map<Position, Piece> board;

    public JanggiBoard(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public static JanggiBoard initializeWithPieces() {
        Map<Position, Piece> board = BoardInitializer.initialPieces(X_LIMIT, Y_LIMIT);
        return new JanggiBoard(board);
    }

    public List<Position> computeReachableDestination(final Position position) {
        validatePositionHasPiece(position);

        Piece piece = board.get(position);
        List<Route> candidatesRoutes = piece.computeCandidatePositions(position);
        List<Position> reachableDestinations = piece.filterReachableDestinations(candidatesRoutes, board);
        validateReachableDestinations(reachableDestinations);
        return reachableDestinations;
    }

    public Piece moveOrCatchPiece(final Position selectedPiecePosition, final Position destination,
                                  final List<Position> reachableDestinations) {
        validateSelectedDestination(destination, reachableDestinations);
        Piece seletedPiece = board.get(selectedPiecePosition);
        board.put(selectedPiecePosition, new Empty());

        Piece destinationPiece = board.get(destination);
        board.put(destination, seletedPiece);

        return destinationPiece;
    }

    public boolean checkGameIsOver(final Piece catchedPiece) {
        return catchedPiece.getType() == PieceType.KING;
    }

    private void validatePositionHasPiece(final Position position) {
        Piece targetPiece = board.get(position);
        if (!targetPiece.isOccupied()) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 움직일 수 있는 기물이 없습니다.");
        }
    }

    private void validateReachableDestinations(final List<Position> reachableDestinations) {
        if (reachableDestinations.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동 가능한 목적지가 존재하지 않습니다.");
        }
    }

    private void validateSelectedDestination(final Position destination, final List<Position> reachableDestinations) {
        if (!reachableDestinations.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 선택한 목적지로 이동할 수 없습니다.");
        }
    }

    public Map<Position, Piece> getBoard() {
        return new HashMap<>(board);
    }
}
