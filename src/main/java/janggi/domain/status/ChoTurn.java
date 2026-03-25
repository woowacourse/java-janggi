package janggi.domain.status;

import janggi.domain.Board;
import janggi.domain.Point;

public class ChoTurn implements GameStatus{

    private final Team team;

    public ChoTurn() {
        this.team = Team.CHO;
    }

    @Override
    public GameStatus move(Point from, Point to, Board board) {
        validateFromPoint(from, board);
        validateOfPoint(to, board);
        return new HanTurn();
    }

    private void validateOfPoint(Point to, Board board) {
        if (!board.isEmptyPoint(to) && board.isSameTeam(to, team)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateFromPoint(Point from, Board board) {
        if (board.isEmptyPoint(from) || !board.isSameTeam(from, team)) {
            throw new IllegalArgumentException();
        }
    }
}
