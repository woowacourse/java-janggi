package janggi.domain.board;

import janggi.domain.Turn;
import janggi.domain.piece.Empty;
import janggi.domain.piece.Piece;

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

    public static JanggiBoard fillEmptyPiece(Map<Position, Piece> positionPieces) {
        for (int i = 0; i < X_LIMIT * Y_LIMIT; i++) {
            Position position = new Position(i / Y_LIMIT, i % Y_LIMIT);
            positionPieces.putIfAbsent(position, new Empty());
        }
        return new JanggiBoard(positionPieces);
    }

    public List<Position> computeReachableDestination(final Turn turn, final Position position) {
        validatePieceSelect(turn, position);

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

    public boolean checkGameIsOver(final Turn turn) {
        Turn enemySide = turn.getEnemySide();
        return board.values().stream()
                .filter(piece -> piece.isSameSide(enemySide))
                .noneMatch(Piece::isKing);
    }

    public int sumSideTotalScore(final Turn turn) {
        return board.values().stream()
                .filter(piece -> piece.isSameSide(turn))
                .mapToInt(piece -> piece.getType().getScore())
                .sum();
    }

    private void validatePieceSelect(final Turn turn, final Position position) {
        validateSideSelectedPiece(turn, position);
        validatePositionHasPiece(position);
    }

    private void validateSideSelectedPiece(final Turn turn, final Position position) {
        Piece piece = board.get(position);
        if (turn == Turn.HAN && piece.isCho()) {
            throw new IllegalArgumentException("[ERROR] 상대편의 기물을 선택하셨습니다. 다시 선택하세요.");
        }
        if (turn == Turn.CHO && piece.isHan()) {
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
