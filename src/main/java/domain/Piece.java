package domain;

import java.util.List;

public abstract class Piece {
    protected Position position;
    private final TeamType teamType;

    protected Piece(Position position, TeamType teamType) {
        this.position = position;
        this.teamType = teamType;
    }

    public void moveTo(Position position){
        this.position = position;
    }

    public abstract boolean canMove(Position position, List<Piece> pieces);

    public Position getPosition() {
        return position;
    }
}
