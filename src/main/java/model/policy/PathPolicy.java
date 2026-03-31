package model.policy;

import model.board.Board;
import model.move.Move;
import model.position.Position;

public abstract class PathPolicy {
    public abstract boolean validatePath(Position pos, Board board);

    public abstract boolean validateDestination(Move move, Board board);
}
