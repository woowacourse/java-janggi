package domain.state;

import domain.board.Board;
import domain.piece.PieceColor;

public class RedTurn extends Playing {

    public RedTurn(Board board) {
        super(board, PieceColor.RED);
    }

    @Override
    protected State nextTurn(Board board) {
        return new BlueTurn(board);
    }
}
