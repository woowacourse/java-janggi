package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Score;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.Movement;
import janggi.domain.rule.block.BasicBlockStrategy;
import janggi.domain.rule.move.CurveMoveStrategy;
import java.util.List;

public class Elephant extends Piece {

    private static final int SCORE = 3;
    private static final Movement MOVEMENT = new Movement(List.of(2, 3));

    public Elephant(final Position position, final Team team) {
        super(position, team, PieceType.Elephant, new MoveRule(new CurveMoveStrategy(), new BasicBlockStrategy()));
    }

    public static List<Elephant> Default(Team team) {
        int defaultRow = Team.decideRow(1, team);
        List<Integer> defaultColumns = List.of(2, 7);

        return defaultColumns.stream()
                .map(defaultColumn -> new Elephant(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination, MOVEMENT);
        return new Elephant(destination, team);
    }

    @Override
    public Score die() {
        return new Score(SCORE);
    }
}
