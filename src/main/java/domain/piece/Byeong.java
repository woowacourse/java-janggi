package domain.piece;

public class Byeong implements Piece {
    private final Team team;

    public Byeong(Team team) {
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
