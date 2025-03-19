package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

import java.util.List;
import java.util.stream.Stream;

public class Soldier extends Piece {

    public Soldier(final Position position, final Team team) {
        super(position, team);
    }

    public static List<Soldier> Default(Team team) {
        int row = getRowByTeam(4, team);

        return Stream.of(1, 3, 5, 7, 9)
                .map(col -> Position.of(row, col))
                .map(position -> new Soldier(position, team))
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
