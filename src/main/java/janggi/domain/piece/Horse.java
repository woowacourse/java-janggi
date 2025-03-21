package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Score;
import janggi.domain.Team;
import java.util.List;

public class Horse extends Piece {

    public static final Movement MOVEMENT = new Movement(List.of(1, 2));

    public Horse(final Position position, final Team team) {
        super(position, team);
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
        validateMove(board, destination);
        return new Horse(destination, team);
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
        return Score.Horse();
    }
}
