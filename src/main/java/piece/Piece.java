package piece;

import board.Board;
import board.Position;

public class Piece {

    private final PieceType pieceType;
    private final TeamType teamType;

    public Piece(final PieceType pieceType, final TeamType teamType) {
        this.pieceType = pieceType;
        this.teamType = teamType;
    }

    public boolean isAbleToMove(final Position now, final Position destination, final Board board,
                                final TeamType teamType) {
        return pieceType.isAbleMove(now, destination, board, teamType);
    }

    public boolean equalsPieceType(final PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public boolean equalsTeamType(final TeamType teamType) {
        return this.teamType == teamType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public TeamType getTeamType() {
        return teamType;
    }
}
