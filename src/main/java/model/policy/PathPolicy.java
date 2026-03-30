package model.policy;

import model.board.Board;
import model.move.Move;
import model.position.Position;

public abstract class PathPolicy {
    public abstract boolean check(Position pos, Board board);

    public abstract boolean validate(Move move, Board board);

    public abstract boolean isValid();
}
