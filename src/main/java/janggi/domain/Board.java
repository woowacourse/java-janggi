package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.vo.Position;
import java.util.List;

public class Board {
    private final List<List<Piece>> board;

    public Board() {
        board = BoardInitializer.createBoard();
    }

    public Piece findByPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return board.get(row).get(col);
    }

    public boolean isEmptyPosition(Position position) {
        return findByPosition(position).isEmpty();
    }


}
