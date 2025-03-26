package janggi.piece;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;

public class Cannon extends Piece {

    public Cannon(TeamName teamName, Position position) {
        this.teamName = teamName;
        this.position = position;
        this.pieceName = PieceName.CANNON;
        this.pieceStatus = PieceStatus.ALIVE;
    }

    @Override
    public void validateMovement(Position currentPosition, Position destination, PalaceArea palaceArea) {
        int offsetX = currentPosition.distanceX(destination);
        int offsetY = currentPosition.distanceY(destination);

        boolean isValidMove = true;
        if (palaceArea == PalaceArea.OUTSIDE) {
            isValidMove = offsetX == OFFSET_ZERO && offsetY <= OFFSET_Y_MAX
                    || offsetX <= OFFSET_X_MAX && offsetY == OFFSET_ZERO;
        }
        if (palaceArea == PalaceArea.INSIDE) {
            isValidMove = offsetX == OFFSET_TWO && offsetY == OFFSET_TWO
                    || offsetX == OFFSET_ZERO && offsetY <= OFFSET_Y_MAX
                    || offsetX <= OFFSET_X_MAX && offsetY == OFFSET_ZERO;
        }
        if (!isValidMove) {
            throw new IllegalArgumentException(INVALID_MOVEMENT);
        }
    }
}
