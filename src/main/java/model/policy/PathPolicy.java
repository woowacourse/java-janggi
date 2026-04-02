package model.policy;

import java.util.List;
import model.board.Board;
import model.board.Country;
import model.move.Move;
import model.position.Position;

public abstract class PathPolicy {
    public abstract boolean validatePath(List<Position> path, Board board);

    public abstract boolean validateDestination(Move move, Board board, Country country);
}
