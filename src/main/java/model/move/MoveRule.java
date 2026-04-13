package model.move;

import java.util.List;

import model.board.Board;
import model.board.Country;
import model.board.Palace;

public abstract class MoveRule {
    public abstract boolean matches(Move move, Board board, Country country);

    protected boolean isPalaceDiagonal(Move move) {
        Palace choPalace = Palace.from(Country.CHO);
        if (choPalace.isDiagonalMove(move)) {
            return true;
        }

        Palace hanPalace = Palace.from(Country.HAN);
        return hanPalace.isDiagonalMove(move);
    }
}
