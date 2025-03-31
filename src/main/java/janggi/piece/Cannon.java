package janggi.piece;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;

public class Cannon extends Piece {

    public Cannon(TeamName teamName, Position position) {
        this.teamName = teamName;
        this.position = position;
        this.pieceName = PieceName.CANNON;
    }

    public Cannon(TeamName teamName, Position position, PieceStatus pieceStatus) {
        this(teamName, position);
        this.pieceStatus = pieceStatus;
    }

    @Override
    public void validateMovement(Position currentPosition, Position destination, PalaceArea palaceArea) {
        int offsetX = currentPosition.distanceX(destination);
        int offsetY = currentPosition.distanceY(destination);

        if (palaceArea.isOutside() && isValidMoveOutsidePalace(offsetX, offsetY)) {
            return;
        }
        if (palaceArea.isInside() && isValidMoveInsidePalace(offsetX, offsetY)) {
            return;
        }
        throw new IllegalArgumentException(INVALID_MOVEMENT);
    }

    private boolean isValidMoveOutsidePalace(int offsetX, int offsetY) {
        return isHorizontalMove(offsetX, offsetY) || isVerticalMove(offsetX, offsetY);
    }

    private boolean isValidMoveInsidePalace(int offsetX, int offsetY) {
        return isDiagonalMove(offsetX, offsetY)
                || isHorizontalMove(offsetX, offsetY) || isVerticalMove(offsetX, offsetY);
    }

    private boolean isHorizontalMove(int offsetX, int offsetY) {
        return offsetX <= OFFSET_X_MAX && offsetY == OFFSET_ZERO;
    }

    private boolean isVerticalMove(int offsetX, int offsetY) {
        return offsetX == OFFSET_ZERO && offsetY <= OFFSET_Y_MAX;
    }

    private boolean isDiagonalMove(int offsetX, int offsetY) {
        return offsetX == OFFSET_TWO && offsetY == OFFSET_TWO;
    }
}
