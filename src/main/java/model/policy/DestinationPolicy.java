package model.policy;

import model.board.Board;
import model.move.Move;

public interface DestinationPolicy {
    boolean validate(Move move, Board board, PathPolicy pathPolicy);
}
