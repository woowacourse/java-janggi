package janggi.board;

import janggi.piece.Empty;
import janggi.piece.Piece;
import janggi.piece.Side;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private static final int X_LIMIT = 9;
    private static final int Y_LIMIT = 10;

    private final Map<Position, Piece> board;

    public JanggiBoard(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public static JanggiBoard initializeWithPieces() {
        Map<Position, Piece> board = BoardInitializer.initialPieces(X_LIMIT, Y_LIMIT);
        return new JanggiBoard(board);
    }

    public List<Position> computeReachableDestination(final Side side, final Position position) {
        validatePieceSelect(side, position);

        Piece piece = board.get(position);
        List<Position> reachableDestinations = piece.computeReachableDestinations(position, board);
        validateReachableDestinations(reachableDestinations);
        return reachableDestinations;
    }

    public Piece moveOrCatchPiece(final Position selectedPiecePosition, final Position destination) {
        Piece seletedPiece = board.get(selectedPiecePosition);
        board.put(selectedPiecePosition, new Empty());

        Piece destinationPiece = board.get(destination);
        board.put(destination, seletedPiece);

        return destinationPiece;
    }

    public boolean checkGameIsOver(final Piece catchedPiece) {
        return catchedPiece.isGameOver();
    }

    private void validatePieceSelect(final Side side, final Position position) {
        validateSideSelectedPiece(side, position);
        validatePositionHasPiece(position);
    }

    private void validateSideSelectedPiece(final Side side, final Position position) {
        Piece piece = board.get(position);
        if (side == Side.HAN && piece.isCho()) {
            throw new IllegalArgumentException("[ERROR] 상대편의 기물을 선택하셨습니다. 다시 선택하세요.");
        }
        if (side == Side.CHO && piece.isHan()) {
            throw new IllegalArgumentException("[ERROR] 상대편의 기물을 선택하셨습니다. 다시 선택하세요.");
        }
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

    public Map<Position, Piece> getBoard() {
        return new HashMap<>(board);
    }
}
