package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

public class General extends Piece {

    public General(final Position position, final Team team) {
        super(position, team);
    }

    public static General Default(Team team) {
        int row = getRowByTeam(2, team);

        return new General(new Position(row, 5), team);
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        if ((board.isExists(destination) && board.isAlly(destination, this.team)) || isInvalidMove(destination)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
        return new General(destination, team);
    }

    private boolean isInvalidMove(Position destination) {
        int diffRow = destination.row() - position.row();
        int diffColumn = destination.column() - position.column();
        if (Math.abs(diffRow) + Math.abs(diffColumn) != 1) {
            return true;
        }
        return false;
    }

    @Override
    public Score die() {
        return Score.Soldier();
    }
}
