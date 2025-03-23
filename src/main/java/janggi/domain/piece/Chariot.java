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

    public Chariot(final Position position, final Team team) {
        super(position, team, PieceType.CHARIOT, new MoveRule(new StraightMoveStrategy(), new BasicBlockStrategy()));
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination, MOVEMENT);
        return new Chariot(destination, team);
    }
}
