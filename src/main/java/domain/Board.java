package domain;

import domain.vo.Position;

import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<Position, Piece> board;

    private Board(final Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board of(final Map<Position, Piece> board) {
        return new Board(board);
    }

    public void tryToMove(final Position from, final Position to, final Team team) {
        Piece fromPiece = findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
        if (fromPiece.getTeam() != team) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 상대편 기물이기 떄문에 움직일 수 없습니다.");
        }

        if (!fromPiece.canMovePiece(from, to, Board.of(this.board))) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 움직일 수 없습니다.");
        }

        movePiece(from, to, fromPiece);
    }

    public boolean isExistPosition(final Position tempPosition) {
        return board.containsKey(tempPosition);
    }

    public Optional<Piece> findPieceByPosition(final Position position) {
        return Optional.ofNullable(board.get(position));
    }

    public boolean canOccupy(Position from, Position to) {
        Piece currentPiece = findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 움직일 기물이 존재하지 않습니다."));

        Optional<Piece> target = findPieceByPosition(to);
        if (target.isEmpty()) {
            return true;
        }

        Piece targetPiece = target.get();
        return currentPiece.isAnotherTeam(targetPiece);
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }

    private void movePiece(Position from, Position to, Piece fromPiece) {
        board.remove(from);
        board.put(to, fromPiece);
    }
}
