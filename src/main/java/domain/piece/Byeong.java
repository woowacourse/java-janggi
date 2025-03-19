package domain.piece;

public class Byeong implements Piece {
    private final Team team;

    public Byeong(Team team) {
        this.team = team;
    }

    @Override
    public boolean isPho() {
        return false;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
