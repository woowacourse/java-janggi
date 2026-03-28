package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Team;

public class SoliderStrategy implements MoveStrategy {
    private final Team team;

    public SoliderStrategy(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextY = to.getY();
        int nextX = to.getX();

        if (team == Team.HAN) {
            if (nextY - preY == 1 && preX == nextX) {
                return true;
            }
            return (Math.abs(nextX - preX) == 1) && (nextY == preY);
        }
        if (preY - nextY == 1 && preX == nextX) {
            return true;
        }
        return (Math.abs(nextX - preX) == 1) && (nextY == preY);
    }
}
