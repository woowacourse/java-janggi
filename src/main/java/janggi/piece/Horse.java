package janggi.piece;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;

public class Horse extends Piece {

    public Horse(TeamName teamName, Position position) {
        this.teamName = teamName;
        this.position = position;
        this.pieceName = PieceName.HORSE;
        this.pieceStatus = PieceStatus.ALIVE;
    }

    @Override
    public void validateMovement(Position currentPosition, Position destination, PalaceArea palaceArea) {
        int offsetX = Math.abs(currentPosition.x() - destination.x());
        int offsetY = Math.abs(currentPosition.y() - destination.y());

        boolean isValidMove = offsetX == OFFSET_ONE && offsetY == OFFSET_TWO
                || offsetX == OFFSET_TWO && offsetY == OFFSET_ONE;
        if (!isValidMove) {
            throw new IllegalArgumentException(INVALID_MOVEMENT);
        }
    }
}
