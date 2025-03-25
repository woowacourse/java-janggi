package janggi.piece;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;

public class Elephant extends Piece {

    public Elephant(TeamName teamName, Position position) {
        this.teamName = teamName;
        this.position = position;
        this.pieceName = PieceName.ELEPHANT;
        this.pieceStatus = PieceStatus.ALIVE;
    }

    @Override
    public void validateMovement(Position currentPosition, Position destination, PalaceArea palaceArea) {
        int offsetX = Math.abs(currentPosition.x() - destination.x());
        int offsetY = Math.abs(currentPosition.y() - destination.y());

        boolean isValidMove = offsetX == OFFSET_TWO && offsetY == OFFSET_THREE
                || offsetX == OFFSET_THREE && offsetY == OFFSET_TWO;
        if (!isValidMove) {
            throw new IllegalArgumentException(INVALID_MOVEMENT);
        }
    }
}
