package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Score;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.Movement;
import janggi.domain.rule.block.BasicBlockStrategy;
import janggi.domain.rule.move.StraightMoveStrategy;
import java.util.List;

public class Chariot extends Piece {

    private static final int SCORE = 13;
    private static final Movement MOVEMENT = new Movement(List.of(0, 1));

    public Chariot(final Position position, final Team team) {
        super(position, team, PieceType.Chariot, new MoveRule(new StraightMoveStrategy(), new BasicBlockStrategy()));
    }

    public static List<Chariot> Default(Team team) {
        int defaultRow = Team.decideRow(1, team);
        List<Integer> defaultColumns = List.of(1, 9);

        return defaultColumns.stream()
                .map(defaultColumn -> new Chariot(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination, MOVEMENT);
        return new Chariot(destination, team);
    }

    @Override
    public Score die() {
        return new Score(SCORE);
    }
}
