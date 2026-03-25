package janggi.domain.status;

import janggi.domain.Board;
import janggi.domain.Point;

public class HanTurn implements GameStatus {

    private final Team team;

    public HanTurn() {
        this.team = Team.HAN;
    }

    @Override
    public GameStatus move(Point from, Point to, Board board) {
        if (board.isKingDie(to, team)) {
            return new FinishedGame(Team.CHO);
        }
        board.move(from, to, team);
        return new ChoTurn();
    }
}
