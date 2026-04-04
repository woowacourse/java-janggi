package janggi.domain.movestrategy.rule;

import janggi.domain.board.BoardState;
import janggi.domain.position.Position;

public class StraightLineRule implements MoveRule {

    @Override
    public boolean isValid(Position from, Position to, BoardState boardState) {
        return from.getRow() == to.getRow() || from.getColumn() == to.getColumn();
    }
}
