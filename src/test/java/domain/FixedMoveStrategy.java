package domain;

import domain.board.Board;
import domain.strategy.MoveStrategy;
import domain.vo.Position;

public class FixedMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        return false;
    }
}
