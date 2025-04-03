package model.piece;

import java.util.Map;
import model.Moving;
import model.Point;
import model.Team;

public class Byeong extends PalaceMovablePieces {

    private static final int MINIMUM_PIECES_COUNT_IN_PATH = 1;
    private static final int BLUE_TEAM_MOVE_FORWARD = 1;
    private static final int RED_TEAM_MOVE_FORWARD = -1;

    public Byeong(Team team) {
        super(team, PieceName.BYEONG,Score.BYEONG);
    }


    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == MINIMUM_PIECES_COUNT_IN_PATH) {
            return piecesOnPathWithTargetOrNot.keySet()
                    .stream()
                    .findFirst()
                    .get()
                    .getTeam() != getTeam();
        }
        return true;
    }

    @Override
    public void validateGungCross(Point beforePoint, Point targetPoint) {
        if (!Palace.ALL_PALACE.isInPalace(targetPoint)) {
            return;
        }
        int moveForward = BLUE_TEAM_MOVE_FORWARD;

        if (getTeam().isRed()) {
            moveForward = RED_TEAM_MOVE_FORWARD;
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
        int moveForward = BLUE_TEAM_MOVE_FORWARD;

        if (getTeam().isRed()) {
            moveForward = RED_TEAM_MOVE_FORWARD;
        }

        if (!((moving.getVectorY() == moveForward || moving.getVectorY() == 0) && (moving.isDistance(1)))) {
            throw new IllegalArgumentException("잘못된 이동입니다.");
        }
    }
}
