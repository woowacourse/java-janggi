package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

public class Soldier extends Piece {

    public Soldier(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        if (board.isExists(destination) && board.isAlly(this.team, destination) && isInvalidMove(destination)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
        return new Soldier(destination, team);
    }

    private boolean isInvalidMove(Position destination) {
        int diffRow = destination.row() - position.row();
        int diffColumn = destination.column() - position.column();
        if (Math.abs(diffRow) + Math.abs(diffColumn) != 1) {
            return true;
        }
        if (diffRow == 0) {
            return false;
        }
        if (this.team == Team.RED && diffRow == 1) {
            return false;
        }
        if (this.team == Team.GREEN && diffRow == -1) {
            return false;
        }
        return true;
    }

    @Override
    public Score die() {
        return Score.Soldier();
    }
}
