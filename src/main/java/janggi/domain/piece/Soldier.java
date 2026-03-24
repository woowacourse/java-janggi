package janggi.domain.piece;

public class Soldier extends Piece {

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public String displayName() {
        return "졸";
    }

}
