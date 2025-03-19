package piece;

import board.Position;

public class Piece {

    private final PieceType pieceType;
    private final TeamType teamType;

    public Piece(final PieceType pieceType, final TeamType teamType) {
        this.pieceType = pieceType;
        this.teamType = teamType;
    }

    public boolean equalsPieceType(final PieceType pieceType){
        return this.pieceType == pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public TeamType getTeamType() {
        return teamType;
    }
}
