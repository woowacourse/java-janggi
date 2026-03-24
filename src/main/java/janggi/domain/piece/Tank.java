package janggi.domain.piece;

public class Tank extends Piece {

    public Tank(Team team) {
        super(team);
    }

    @Override
    public String displayName() {
        return "차";
    }
}
