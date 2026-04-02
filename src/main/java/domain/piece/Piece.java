package domain.piece;

import domain.team.Team;

public abstract class Piece {

    protected final Team team;
    protected final PieceType pieceType;

    protected Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    protected Piece(PieceType pieceType) {
        this.team = null;
        this.pieceType = pieceType;
    }

    public PieceType pieceType() {
        return pieceType;
    }

    public Team team() {
        return team;
    }

    public boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    public boolean isCho() {
        return this.team == Team.CHO;
    }

    public boolean isSamePiece(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public boolean isSamePiece(Piece piece) {
        return isSamePiece(piece.pieceType);
    }

    public boolean hasPiece() {
        return this.pieceType != PieceType.NONE;
    }

    public String getChineseCharacter() {
        return pieceType.getChineseCharacter(team);
    }

}
