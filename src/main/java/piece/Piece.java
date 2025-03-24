package piece;

import board.Board;
import board.Position;

public abstract class Piece {

    protected final TeamType teamType;

    public Piece(final TeamType teamType) {
        this.teamType = teamType;
    }

    public boolean isAbleToMove(final Position now, final Position destination, final Board board){
        if (board.existPieceByPosition(destination) && board.equalsTeamTypeByPosition(destination, teamType)) {
            return false;
        }
        return canMove(now, destination, board);
    };

    public boolean canMove(Position src, Position dest, Board board) {
        double distanceByPositions = src.calculateDistance(dest);

        if (!withInDirection(src, dest)) {
            return false;
        }
        if (!withInRangeByMovement(distanceByPositions)) {
            return false;
        }
        if (!passFilter(src, dest, board)) {
            return false;
        }
        return true;
    }

    protected abstract boolean withInDirection(Position src, Position destination);

    protected abstract boolean withInRangeByMovement(double distanceByPositions);

    protected abstract boolean passFilter(Position src, Position destination, Board board);

    public abstract boolean equalsType(final Piece piece);

    public boolean equalsTeamType(final TeamType teamType) {
        return this.teamType == teamType;
    }

    public TeamType getTeamType() {
        return teamType;
    }
}
