package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public abstract class ActivePiece implements Piece {
    private final Team team;
    private final PieceType type;

    protected ActivePiece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    @Override
    public boolean isAlly(Piece other) {
        return other instanceof ActivePiece activePiece && this.team == activePiece.team;
    }

    protected int forwardDirection() {
        return team.forwardRowDirection();
    }

    @Override
    public String display(PieceAppearance colorizer) {
        return colorizer.colorize(team, type);
    }

    @Override
    public String toString() {
        return type.name();
    }

    @Override
    public boolean isNotEmpty() {
        return true;
    }
}
