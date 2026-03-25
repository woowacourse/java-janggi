package janggi.domain.mouveRule;

import janggi.domain.Board;
import janggi.domain.vo.Position;

public interface MoveRule {
    public void move(Position from, Position to, Board board);
}
