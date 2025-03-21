package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Score;
import janggi.domain.Team;
import java.util.List;

public class Soldier extends Piece {

    public Soldier(final Position position, final Team team) {
        super(position, team);
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
        validateMove(board, destination);
        return new Soldier(destination, team);
    }

    @Override
    protected void validateCorrectRule(Position destination) {
        int diffRow = destination.subtractRow(this.position);
        int diffColumn = destination.subtractColumn(this.position);

        if (Math.abs(diffRow) + Math.abs(diffColumn) != 1) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }

        if (this.team.isRed() && diffRow == -1) {
            throw new IllegalArgumentException("병은 본진을 향할 수 없습니다.");
        }

        if (this.team.isGreen() && diffRow == 1) {
            throw new IllegalArgumentException("졸은 본진을 향할 수 없습니다.");
        }
    }

    @Override
    public Score die() {
        return Score.Soldier();
    }
}
