package domain.piece;

import domain.Position;
import domain.TeamType;
import java.util.List;

public abstract class Piece {
    protected Position position;
    protected final TeamType teamType;

    protected Piece(Position position, TeamType teamType) {
        this.position = position;
        this.teamType = teamType;
    }

    public void moveTo(Position position){
        this.position = position;
    }

    public boolean hasSamePosition(Position position){
        return this.position.equals(position);
    }

    public boolean isSameTeam(Piece piece){
        return this.teamType.equals(piece.teamType);
    }

    public abstract boolean canMove(Position expectedPosition, List<Piece> pieces);

    public Position getPosition() {
        return position;
    }
}
