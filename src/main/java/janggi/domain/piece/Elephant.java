package janggi.domain.piece;

public class Elephant extends Piece {

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public String displayName() {
        return "상";
    }
}
