package janggi.domain;

public class Piece {
    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public String getTeamName() {
        return team.getName();
    }

    public String getPieceTypeName(){
        return pieceType.getName();
    }
}
