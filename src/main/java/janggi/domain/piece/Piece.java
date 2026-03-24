package janggi.domain.piece;

public abstract class Piece implements State {
    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public boolean isSameTeam(Team otherTeam) {
        return team == otherTeam;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Team findTeam() {
        return team;
    }
}
