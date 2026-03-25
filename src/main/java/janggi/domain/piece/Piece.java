package janggi.domain.piece;

public abstract class Piece {
    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public boolean isSameTeam(Team otherTeam) {
        return team == otherTeam;
    }

    public boolean isEmpty() {
        return false;
    }

    public Team findTeam() {
        return team;
    }

    public abstract PieceType pieceType();

    //public MoveRule moveRule


}
