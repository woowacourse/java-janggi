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
    public boolean validateMovement(Position currentPosition, Position destination, PalaceArea palaceArea) {
        int offsetX = Math.abs(currentPosition.x() - destination.x());
        int offsetY = Math.abs(currentPosition.y() - destination.y());

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
        return true;
    }

    @Override
    public void updateStatusIfCaught(Position opponentPosition) {
        if (this.position.equals(opponentPosition)) {
            this.pieceStatus = PieceStatus.CAUGHT;
        }
    }
}
