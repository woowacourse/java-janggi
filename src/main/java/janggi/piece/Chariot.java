package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

import java.util.List;

public class Chariot extends Piece {

    public Chariot(final Position position, final Team team) {
        super(position, team);
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
        validateMove(board, destination);
        return new Chariot(destination, team);
    }

    @Override
    protected void validateCorrectRule(Position destination) {
        int diffRow = destination.subtractRow(this.position);
        int diffColumn = destination.subtractColumn(this.position);

        int maxDiff = Math.max(Math.abs(diffRow), Math.abs(diffColumn));
        int minDiff = Math.min(Math.abs(diffRow), Math.abs(diffColumn));

        if (maxDiff != 0 && minDiff != 0) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }

    @Override
    public Score die() {
        return Score.Chariot();
    }
}
