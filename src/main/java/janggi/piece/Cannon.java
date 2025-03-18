package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

public class Cannon extends Piece {

    public Cannon(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination);
        return new Cannon(destination, team);
    }

    private void validateMove(final Board board, final Position destination) {
        if (isAllyInDestination(board, destination) || isInvalidMove(board, destination)) {
            throw new IllegalArgumentException("이동할 수 없는 지점입니다.");
        }
    }

    private boolean isAllyInDestination(final Board board, final Position destination) {
        return board.isExists(destination) && board.isAlly(destination, this.team);
    }

    @Override
    public Score die() {
        return Score.Cannon();
    }

    private boolean isInvalidMove(final Board board, final Position destination) {
        return destination.row() != position.row() && destination.column() != position.column();
    }
}
