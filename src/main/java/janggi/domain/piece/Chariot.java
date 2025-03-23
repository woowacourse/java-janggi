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

    public static List<Chariot> Default(Team team) {
        int defaultRow = team.decideRow(1);
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
}
