package janggi.piece;

import janggi.board.Position;

import java.util.Map;

public abstract class Piece {

    protected final String team;

    public Piece(String team) {
        this.team = team;
    }

    public boolean isDifferentTeam(String team) {
        return this.team.equals(team);
    }

    protected void validateSameTeamOnGoal(Map<Position, Piece> board, Position goal) {
        Piece other = board.get(goal);
        if (!other.isDifferentTeam(team)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 같은 진영의 기물이 있어 이동할 수 없습니다.");
        }
    }

    public abstract void validateMovable(Map<Position, Piece> board, Position start, Position goal);
}
