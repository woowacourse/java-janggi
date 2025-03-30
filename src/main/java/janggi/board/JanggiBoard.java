package janggi.board;

import janggi.piece.Empty;
import janggi.piece.Piece;
import janggi.piece.Side;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private static final int X_LIMIT = 9;
    private static final int Y_LIMIT = 10;

    private final Map<Position, Piece> board;
    private BoardStatus status;

    private JanggiBoard(final Map<Position, Piece> board, final BoardStatus status) {
        this.board = board;
        this.status = status;
    }

    public static JanggiBoard initialize() {
        Map<Position, Piece> board = BoardInitializer.initialPieces(X_LIMIT, Y_LIMIT);
        return new JanggiBoard(board, BoardStatus.CHO_TURN);
    }

    public List<Position> computeReachableDestination(final Position position) {
        Piece piece = board.get(position);
        validatePositionHasPiece(piece);
        validateSelectCurrentTurnPiece(piece);

        List<Route> candidatesRoutes = piece.computeCandidateRoutes(position);
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

    public void checkGameIsOver(final Piece catchedPiece) {
        if (catchedPiece.isKing() && catchedPiece.isHan()) {
            status = BoardStatus.CHO_WIN;
        }
        if (catchedPiece.isKing() && catchedPiece.isCho()) {
            status = BoardStatus.HAN_WIN;
        }
    }

    public void passTurnToOpponent() {
        if (status == BoardStatus.HAN_TURN) {
            status = BoardStatus.CHO_TURN;
            return;
        }
        if (status == BoardStatus.CHO_TURN) {
            status = BoardStatus.HAN_TURN;
        }
    }

    public boolean isGameProgress() {
        return status == BoardStatus.CHO_TURN || status == BoardStatus.HAN_TURN;
    }

    public boolean isGameEnd() {
        return status == BoardStatus.CHO_WIN || status == BoardStatus.HAN_WIN;
    }

    public boolean isPositionCannon(final Position position) {
        return findPieceBy(position).isCannon();
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
        return board.get(position).isEmpty();
    }

    private void validatePositionHasPiece(final Piece piece) {
        if (piece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 움직일 수 있는 기물이 없습니다.");
        }
    }

    private void validateSelectCurrentTurnPiece(final Piece piece) {
        if (piece.isCho() && status.getSide() == Side.HAN || piece.isHan() && status.getSide() == Side.CHO) {
            throw new IllegalArgumentException("[ERROR] 자신의 차례에 맞는 기물을 선택해 주세요.");
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

    public BoardStatus getStatus() {
        return status;
    }
}
