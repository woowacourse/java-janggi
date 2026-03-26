package domain;

import java.util.Map;
import java.util.NoSuchElementException;

public class Board {
    private static final String NO_PIECE_EXIST_ERROR_MESSAGE = "[ERROR] 해당 좌표에 기물이 없습니다.";
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Piece findBy(Position position) {
        Piece piece = board.get(position);

        if (piece == null) {
            throw new NoSuchElementException(NO_PIECE_EXIST_ERROR_MESSAGE);
        }
        return piece;
    }
}
