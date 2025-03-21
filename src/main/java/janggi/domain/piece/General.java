package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Score;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.Movement;
import janggi.domain.rule.block.BasicBlockStrategy;
import janggi.domain.rule.move.OneStepMoveStrategy;
import java.util.List;

public class General extends Piece {

    private static final int SCORE = 0;
    private static final Movement MOVEMENT = new Movement(List.of(0, 1));

    public General(final Position position, final Team team) {
        super(position, team, PieceType.General, new MoveRule(new OneStepMoveStrategy(), new BasicBlockStrategy()));
    }

    public static General Default(Team team) {
        int defaultRow = Team.decideRow(2, team);
        int defaultColumn = 5;

        return new General(Position.of(defaultRow, defaultColumn), team);
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination, MOVEMENT);
        return new General(destination, team);
    }

    @Override
    public Score die() {
        return new Score(SCORE);
    }
}
