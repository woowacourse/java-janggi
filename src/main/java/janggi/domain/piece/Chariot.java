package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.Movement;
import janggi.domain.rule.block.BasicBlockStrategy;
import janggi.domain.rule.move.StraightMoveStrategy;
import java.util.List;

public class Chariot extends Piece {

    private static final Movement MOVEMENT = new Movement(List.of(0, 1));

    public Chariot(final Team team) {
        super(team, PieceType.CHARIOT, new MoveRule(new StraightMoveStrategy(), new BasicBlockStrategy()));
    }

    @Override
    public void checkCanMove(final Board board, final Position departure, final Position destination) {
        validateMove(board, departure, destination, MOVEMENT);
    }
}
