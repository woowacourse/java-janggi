package janggi.domain.board;

import janggi.domain.game.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public void move(final Position start, final Position end, final Turn turn) {
        validatePieceOnStartPosition(start);
        Piece pickedPiece = board.get(start);
        validatePickedPieceBySide(pickedPiece, turn);
        validateEndPosition(end, pickedPiece);

        if (pickedPiece.canMove(start, end, board)) {
            board.remove(start);
            board.put(end, pickedPiece);
            return;
        }

        throw new IllegalArgumentException("이동할 수 없습니다.");
    }

    public List<Piece> piecesBySide(final Side side) {
        return board.values()
                .stream()
                .filter(piece -> piece.isSameSide(side))
                .toList();
    }

    private void validateEndPosition(final Position end, final Piece pickedPiece) {
        if (board.containsKey(end)) {
            Piece targetPiece = board.get(end);
            validateTargetPiece(pickedPiece, targetPiece);
        }
    }

    private static void validateTargetPiece(final Piece pickedPiece, final Piece targetPiece) {
        if (pickedPiece.isSameSide(targetPiece)) {
            throw new IllegalArgumentException("같은 팀이 있는 위치로는 이동할 수 없습니다.");
        }
    }

    private void validatePieceOnStartPosition(final Position position) {
        if (board.containsKey(position)) {
            return;
        }
        throw new IllegalArgumentException("선택된 좌표에 말이 없습니다.");
    }

    private void validatePickedPieceBySide(final Piece pickedPiece, final Turn turn) {
        if (pickedPiece.isSameSide(turn.getSide())) {
            return;
        }
        throw new IllegalArgumentException("본인 팀 말을 선택해주세요.");
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }
}
