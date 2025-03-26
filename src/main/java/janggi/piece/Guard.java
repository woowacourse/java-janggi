package janggi.piece;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;

public class Guard extends Piece {

    public Guard(TeamName teamName, Position position) {
        this.teamName = teamName;
        this.position = position;
        this.pieceName = PieceName.GUARD;
        this.pieceStatus = PieceStatus.ALIVE;
    }

    @Override
    public void validateMovement(Position currentPosition, Position destination, PalaceArea palaceArea) {
        int offsetX = currentPosition.distanceX(destination);
        int offsetY = currentPosition.distanceY(destination);

        boolean isValidMove = true;
        if (palaceArea == PalaceArea.OUTSIDE) {
            isValidMove = offsetX == OFFSET_ONE && offsetY == OFFSET_ZERO
                    || offsetX == OFFSET_ZERO && offsetY == OFFSET_ONE;
        }
        if (palaceArea == PalaceArea.INSIDE) {
            isValidMove = offsetX == OFFSET_ONE && offsetY == OFFSET_ONE
                    || offsetX == OFFSET_ONE && offsetY == OFFSET_ZERO
                    || offsetX == OFFSET_ZERO && offsetY == OFFSET_ONE;
        }
        if (!isValidMove) {
            throw new IllegalArgumentException(INVALID_MOVEMENT);
        }
    }
}
