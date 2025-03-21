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

public class Horse extends Piece {

    private static final int SCORE = 5;
    private static final Movement MOVEMENT = new Movement(List.of(1, 2));

    public Horse(final Position position, final Team team) {
        super(position, team, PieceType.Horse, new MoveRule(new CurveMoveStrategy(), new BasicBlockStrategy()));
    }

    public static List<Horse> Default(Team team) {
        int defaultRow = Team.decideRow(1, team);
        List<Integer> defaultColumns = List.of(3, 8);

        return defaultColumns.stream()
                .map(defaultColumn -> new Horse(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination, MOVEMENT);
        return new Horse(destination, team);
    }

    @Override
    public Score die() {
        return new Score(SCORE);
    }
}
