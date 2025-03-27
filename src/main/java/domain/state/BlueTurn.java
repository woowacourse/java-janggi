package domain.state;

import domain.board.Board;
import domain.piece.PieceColor;

public class BlueTurn extends Playing {

    public BlueTurn(Board board) {
        super(board, PieceColor.BLUE);
    }

    @Override
    protected State nextTurn(Board board) {
        return new RedTurn(board);
    }
}
