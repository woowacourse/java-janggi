package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Score;
import janggi.domain.Team;
import java.util.List;

public class Elephant extends Piece {

    public static final Movement MOVEMENT = new Movement(List.of(2, 3));

    public Elephant(final Position position, final Team team) {
        super(position, team, PieceType.Elephant);
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
        validateMove(board, destination);
        return new Elephant(destination, team);
    }

    @Override
    protected void validateCorrectRule(Position destination) {
        int diffRow = destination.subtractRow(this.position);
        int diffColumn = destination.subtractColumn(this.position);

        int maxDiff = Math.max(Math.abs(diffRow), Math.abs(diffColumn));
        int minDiff = Math.min(Math.abs(diffRow), Math.abs(diffColumn));

        if (maxDiff != MOVEMENT.getMaxDistance() || minDiff != MOVEMENT.getMinDistance()) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }

    @Override
    public Score die() {
        return Score.Elephant();
    }
}
