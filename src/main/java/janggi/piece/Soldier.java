package janggi.piece;

import janggi.board.Position;

import java.util.Map;

public class Soldier extends Piece {

    public Soldier(String team) {
        super(team);
    }

    @Override
    public boolean canMove(Map<Position, Piece> board, Position start, Position goal) {
        Position position = checkGoal(start, goal);
        return board.get(position).isDifferentTeam(this.team);
    }

    private Position checkGoal(Position start, Position goal) {
        Position leftPosition = start.minusColumn();
        Position rightPosition = start.plusColumn();
        Position upPosition = start.plusRow();

        if (goal.equals(leftPosition)) {
            return leftPosition;
        }

        if (goal.equals(rightPosition)) {
            return leftPosition;
        }

        if (goal.equals(upPosition)) {
            return leftPosition;
        }

        throw new IllegalArgumentException("[ERROR] 졸/병은 해당 목적지로 이동할 수 없습니다.");
    }

}
