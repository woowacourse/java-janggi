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

    public void move(final Position from, final Position to) {
        Piece fromPiece = findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));

        if (!fromPiece.canMovePiece(from, to, this)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치로 움직일 수 없습니다.");
        }

        board.remove(from);
        board.put(to, fromPiece);
    }

    public boolean isExistPosition(final Position tempPosition) {
        return board.containsKey(tempPosition);
    }

    public Optional<Piece> findPieceByPosition(final Position position) {
        return Optional.ofNullable(board.get(position));
    }

    public boolean isAnotherTeam(final Position from, final Position to) {
        if (findPieceByPosition(to).isEmpty()) {
            return true;
        }

        Piece currentPiece = findPieceByPosition(from).get();
        Piece targetPiece = findPieceByPosition(to).get();
        return currentPiece.isAnotherTeam(targetPiece);
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
