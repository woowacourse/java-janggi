package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

public class Horse extends Piece {

    public Horse(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        if ((board.isExists(destination) && board.isAlly(destination, this.team)) || isInvalidMove(destination)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
        return new Horse(destination, team);
    }

    private boolean isInvalidMove(Position destination) {
        int diffRow = destination.row() - position.row();
        int diffColumn = destination.column() - position.column();

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
