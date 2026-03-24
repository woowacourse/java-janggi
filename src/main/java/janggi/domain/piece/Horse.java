package janggi.domain.piece;

public class Horse extends Piece {

    public Horse(Team team) {
        super(team);
    }

    @Override
    public String displayName() {
        return "마";
    }
}
