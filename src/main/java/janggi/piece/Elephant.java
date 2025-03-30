package janggi.piece;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;

public class Elephant extends Piece {

    public Elephant(TeamName teamName, Position position) {
        this.teamName = teamName;
        this.position = position;
        this.pieceName = PieceName.ELEPHANT;
    }

    public Elephant(TeamName teamName, Position position, PieceStatus pieceStatus) {
        this(teamName, position);
        this.pieceStatus = pieceStatus;
    }

    @Override
    public void validateMovement(Position currentPosition, Position destination, PalaceArea palaceArea) {
        int offsetX = currentPosition.distanceX(destination);
        int offsetY = currentPosition.distanceY(destination);

        isDiagonalMove(offsetX, offsetY);
    }

    private void isDiagonalMove(int offsetX, int offsetY) {
        if (offsetX == OFFSET_TWO && offsetY == OFFSET_THREE) {
            return;
        }
        if (offsetX == OFFSET_THREE && offsetY == OFFSET_TWO) {
            return;
        }
        throw new IllegalArgumentException(INVALID_MOVEMENT);
    }
}
