package janggi.domain.piece;

import janggi.domain.board.Point;
import janggi.domain.camp.Camp;
import janggi.domain.piece.type.MoveType;
import janggi.domain.piece.type.PieceType;

public final class Soldier extends Piece {

    private static final int SOLDIER_MOVE_DISTANCE = 1;
    private static final String HAN_NAME = "병";
    private static final String CHU_NAME = "졸";

    public Soldier(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMovementRule(MoveType moveType, Point from, Point to) {
        String name = getName();
        if (moveType.isPalace()) {
            validatePalaceSoldierMove(name, from, to);
            return;
        }
        validateNormalSoldierMove(name, from, to);
    }

    private void validateNormalSoldierMove(String name, Point from, Point to) {
        if (isMovingBackward(from, to)) {
            throw new IllegalArgumentException("%s은 뒤로 갈 수 없습니다.".formatted(name));
        }
        if (!isSoldierMove(from, to)) {
            throw new IllegalArgumentException("%s은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.".formatted(name));
        }
    }

    private void validatePalaceSoldierMove(String name, Point from, Point to) {
        if (isMovingBackward(from, to)) {
            throw new IllegalArgumentException("%s은 뒤로 갈 수 없습니다.".formatted(name));
        }
        if (!isSoldierPalaceMove(from, to)) {
            throw new IllegalArgumentException("%s은 궁성 안에서 앞 또는 양 옆, 대각선으로 한 칸만 움직여야 합니다.".formatted(name));
        }
    }

    private boolean isMovingBackward(Point from, Point to) {
        if (isSameCamp(Camp.CHU)) {
            return from.isYGreaterThan(to);
        }
        return to.isYGreaterThan(from);
    }

    private boolean isSoldierMove(Point from, Point to) {
        int moveDistance = from.xDistanceTo(to) + from.yDistanceTo(to);
        return moveDistance == SOLDIER_MOVE_DISTANCE;
    }

    private boolean isSoldierPalaceMove(Point from, Point to) {
        if (from.isDiagonallyAlignedWith(to)) {
            return from.xDistanceTo(to) == SOLDIER_MOVE_DISTANCE && from.yDistanceTo(to) == SOLDIER_MOVE_DISTANCE;
        }
        return isSoldierMove(from, to);
    }

    public String getName() {
        if (isSameCamp(Camp.CHU)) {
            return CHU_NAME;
        }
        return HAN_NAME;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.SOLDIER;
    }
}
