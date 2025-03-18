package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

import java.util.List;
import java.util.stream.Stream;

public class Elephant extends Piece {

    public Elephant(final Position position, final Team team) {
        super(position, team);
    }

    public static List<Elephant> Default(Team team) {
        int row = getRowByTeam(1, team);

        return Stream.of(2, 8)
                .map(col -> new Position(row, col))
                .map(position -> new Elephant(position, team))
                .toList();
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        if ((board.isExists(destination) && board.isAlly(destination, this.team)) || isInvalidMove(destination)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
        return new Elephant(destination, team);
    }

    private boolean isInvalidMove(Position destination) {
        int diffRow = destination.row() - position.row();
        int diffColumn = destination.column() - position.column();

        if ((Math.abs(diffRow) != 2 || Math.abs(diffColumn) != 3)
                && (Math.abs(diffRow) != 3 || Math.abs(diffColumn) != 3)) {
            return true;
        }
        return false;
    }

    @Override
    public Score die() {
        return Score.Elephant();
    }
}
