package movementRule.linearMover;

import java.util.List;
import movementRule.PieceRule;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public abstract sealed class LinearMover
        implements PieceRule
        permits Po, Cha {

    private final Position HAN_CENTER = new Position(1, 4);
    private final Position CHO_CENTER = new Position(8, 4);

    @Override
    public int getScore() {
        return 7;
    }

    @Override
    public void canMoveTo(final Position startPosition, Position destination) {
        if (isInvalidLinearMove(startPosition, destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position startPosition, Position destination) {
        Positions route = new Positions(List.of());
        int dRow = startPosition.calculateDRow(destination);
        int dCol = startPosition.calculateDCol(destination);

        if (startPosition.isLeftwardTo(destination)) {
            addLeftwardRoute(startPosition, dCol, route);
        }

        if (startPosition.isRightwardTo(destination)) {
            addRightwardRoute(startPosition, dCol, route);
        }

        if (startPosition.isUpwardTo(destination)) {
            addUpwardRoute(startPosition, dRow, route);
        }

        if (startPosition.isDownwardTo(destination)) {
            addDownwardRoute(startPosition, dRow, route);
        }

        if (startPosition.isTwoStepDiagonalMoveForLinearMoverInCho(destination)) {
            route.addPosition(CHO_CENTER);
        }

        if (startPosition.isTwoStepDiagonalMoveForLinearMoverInHan(destination)) {
            route.addPosition(HAN_CENTER);
        }

        return route;
    }

    private boolean isInvalidLinearMove(final Position startPosition, final Position destination) {
        return !startPosition.isSameRow(destination) && !startPosition.isSameCol(destination) && !startPosition.isDiagonalMoveForLinearMover(destination);
    }

    private void addDownwardRoute(final Position startPosition, final int dRow, Positions route) {
        for (int i = 1; i < Math.abs(dRow); i++) {
            route.addPosition(startPosition.calculateMovement(i, 0));
        }
    }

    private void addUpwardRoute(final Position startPosition, final int dRow, Positions route) {
        for (int i = 1; i < dRow; i++) {
            route.addPosition(startPosition.calculateMovement(-i, 0));
        }
    }

    private void addRightwardRoute(final Position startPosition, final int dCol, Positions route) {
        for (int i = 1; i < Math.abs(dCol); i++) {
            route.addPosition(startPosition.calculateMovement(0, i));
        }
    }

    private void addLeftwardRoute(final Position startPosition, final int dCol, Positions route) {
        for (int i = 1; i < dCol; i++) {
            route.addPosition(startPosition.calculateMovement(0, -i));
        }
    }

}
