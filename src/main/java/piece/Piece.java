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

    public abstract boolean canMove(final Position now, final Position destination, final Board board);

    public abstract boolean equalsType(final Piece piece);

    public boolean equalsTeamType(final TeamType teamType) {
        return this.teamType == teamType;
    }

    public TeamType getTeamType() {
        return teamType;
    }
}
