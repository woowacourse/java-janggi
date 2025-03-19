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

        return new General(Position.of(row, 5), team);
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination);
        return new General(destination, team);
    }

    @Override
    protected void validateCorrectRule(Position destination) {
        int diffRow = destination.subtractRow(this.position);
        int diffColumn = destination.subtractColumn(this.position);

        if (Math.abs(diffRow) + Math.abs(diffColumn) != 1) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }

    @Override
    public Score die() {
        return Score.Soldier();
    }
}
