package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;
import java.util.List;
import java.util.stream.Stream;

public class Guard extends Piece {

    public Guard(final Position position, final Team team) {
        super(position, team);
    }

    public static List<Guard> Default(Team team) {
        int row = getRowByTeam(1, team);

        return Stream.of(4, 6)
                .map(col -> Position.of(row, col))
                .map(position -> new Guard(position, team))
                .toList();
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        if ((board.isExists(destination) && board.isAlly(destination, this.team)) || isInvalidMove(destination)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
        return new Guard(destination, team);
    }

    private boolean isInvalidMove(Position destination) {
        int diffRow = destination.getRow() - position.getRow();
        int diffColumn = destination.getColumn() - position.getColumn();
        if (Math.abs(diffRow) + Math.abs(diffColumn) != 1) {
            return true;
        }
        return false;
    }

    @Override
    public Score die() {
        return Score.Guard();
    }
}
