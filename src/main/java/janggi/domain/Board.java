package janggi.domain;

import janggi.domain.piece.Piece;
import java.util.List;

public class Board {
    private final List<List<Piece>> board;

    public Board() {
        board = BoardInitializer.createBoard();
    }
}
