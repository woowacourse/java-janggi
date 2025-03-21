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

public class Soldier extends Piece {

    private static final int SCORE = 2;
    private static final Movement MOVEMENT = new Movement(List.of(0, 1));

    public Soldier(final Position position, final Team team) {
        super(position, team, PieceType.Soldier, new MoveRule(new OneStepMoveStrategy(), new BasicBlockStrategy()));
    }

    public static List<Soldier> Default(Team team) {
        int defaultRow = Team.decideRow(4, team);
        List<Integer> defaultColumns = List.of(1, 3, 5, 7, 9);

        return defaultColumns.stream()
                .map(defaultColumn -> new Soldier(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination, MOVEMENT);
        validateSoldierRestrict(destination);
        return new Soldier(destination, team);
    }

    private void validateSoldierRestrict(Position destination) {
        int diffRow = destination.subtractRow(this.position);

        if (this.team.isRed() && diffRow == -1) {
            throw new IllegalArgumentException("병은 본진을 향할 수 없습니다.");
        }

        if (this.team.isGreen() && diffRow == 1) {
            throw new IllegalArgumentException("졸은 본진을 향할 수 없습니다.");
        }
    }

    @Override
    public Score die() {
        return new Score(SCORE);
    }
}
