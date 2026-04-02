package model.move;

import java.util.List;

import model.board.Board;
import model.board.Country;

public abstract class MoveRule {
    public abstract boolean matches(Move move, Board board, Country country);
}
