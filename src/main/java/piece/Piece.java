package piece;

import board.Position;

public class Piece {

    private final PieceType pieceType;
    private final Position position;
    private final TeamType teamType;

    public Piece(final PieceType pieceType, final Position position, final TeamType teamType) {
        this.pieceType = pieceType;
        this.position = position;
        this.teamType = teamType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Position getPosition() {
        return position;
    }

    public TeamType getTeamType() {
        return teamType;
    }
}
