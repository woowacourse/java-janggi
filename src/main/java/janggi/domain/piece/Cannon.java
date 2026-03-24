package janggi.domain.piece;

public class Cannon extends Piece{

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public String displayName() {
        return "포";
    }
}
