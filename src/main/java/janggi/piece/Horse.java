package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;
import java.util.List;
import java.util.stream.Stream;

public class Horse extends Piece {

    public Horse(final Position position, final Team team) {
        super(position, team);
    }

    public static List<Horse> Default(Team team) {
        int row = getRowByTeam(1, team);

        return Stream.of(3, 7)
                .map(col -> Position.of(row, col))
                .map(position -> new Horse(position, team))
                .toList();
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        if ((board.isExists(destination) && board.isAlly(destination, this.team)) || isInvalidMove(destination)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
        return new Horse(destination, team);
    }

    private boolean isInvalidMove(Position destination) {
        int diffRow = destination.getRow() - position.getRow();
        int diffColumn = destination.getColumn() - position.getColumn();

        if ((Math.abs(diffRow) != 2 || Math.abs(diffColumn) != 1)
                && (Math.abs(diffRow) != 1 || Math.abs(diffColumn) != 2)) {
            return true;
        }
        return false;
    }

    @Override
    public Score die() {
        return Score.Horse();
    }
}
