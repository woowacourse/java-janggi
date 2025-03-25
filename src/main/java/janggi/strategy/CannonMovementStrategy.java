package janggi.strategy;

import janggi.board.Position;

public class CannonMovementStrategy implements MovementStrategy {

    @Override
    public boolean canReachGoal(Position start, Position goal) {
        if (start.getColumn() == goal.getColumn()) {
            int rowDifference = start.calculatesRowDifference(goal);
            for (int i = 0; i < rowDifference; i++) {

            }
        }

        if (start.getRow() == goal.getRow()) {

        }

        for (int i = 1; i <= 8; i++) {
            Position downPosition = start.down(i);
            Position leftPosition = start.left(i);
            Position rightPosition = start.right(i);

        }

        throw new IllegalArgumentException("[ERROR] 포는 지정한 목적지로 이동할 수 없습니다.");
    }

//    private boolean checkUpPosition(Position start, Position goal) {
//        for (int i = 1; i <= 8; i++) {
//            Position upPosition = start.up(i);
//            if (goal.equals(upPosition)) {
//                return true;
//            }
//        }
//    }
}
