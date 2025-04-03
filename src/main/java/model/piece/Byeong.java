package model.piece;

import java.util.Map;
import model.Moving;
import model.Point;
import model.Team;

public class Byeong extends PalaceMovablePieces {

    public Byeong(Team team) {
        super(team, PieceName.BYEONG,Score.BYEONG);
    }


    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            return piecesOnPathWithTargetOrNot.keySet().stream()
                    .findFirst()
                    .get()
                    .getTeam() != getTeam();
        }
        return true;
    }

    @Override
    public void validateGungCross(Point beforePoint, Point targetPoint) {
        if (!Palace.ALL_PALACE.getPoints().contains(targetPoint)) {
            return;
        }
        int moveForward = 1;

        if (getTeam().isRed()) {
            moveForward = -1;
        }
        Moving moving = new Moving(beforePoint, targetPoint);

        if (moving.getDistance() == 0 || !isValidGoongCrossMovement(moving, moveForward)) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    private boolean isValidGoongCrossMovement(Moving moving, int moveForward) {
        return (moving.getVectorY() == 0 && moving.isDistance(1)) || // 가로 이동
                (moving.getVectorY() == moveForward && moving.isDistance(1)) || // 앞으로 한 칸 이동
                (moving.getVectorY() == moveForward && moving.isDistance(2));   // 대각선 이동
    }

    @Override
    public void validateMovement(Point beforePoint, Point targetPoint) {

        Moving moving = new Moving(beforePoint, targetPoint);
        int moveForward = 1;

        if (getTeam().isRed()) {
            moveForward = -1;
        }

        if (!((moving.getVectorY() == moveForward || moving.getVectorY() == 0) && (moving.isDistance(1)))) {
            throw new IllegalArgumentException("잘못된 이동입니다.");
        }
    }
}
