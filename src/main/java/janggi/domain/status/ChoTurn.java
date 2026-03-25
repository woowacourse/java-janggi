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
        if (board.isKingDie(to, team)) {
            return new FinishedGame(Team.HAN);
        }
        board.move(from, to, team);
        return new HanTurn();
    }
}
