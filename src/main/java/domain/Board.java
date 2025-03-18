package domain;

import domain.piece.Pieces;

import java.util.Map;

public class Board {

    private final Map<Player, Pieces> board;

    public Board(final Map<Player, Pieces> board) {
        this.board = board;
    }

    public Map<Player, Pieces> getBoard() {
        return board;
    }
}
