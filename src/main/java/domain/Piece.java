package domain;

import domain.strategy.MoveStrategy;
import domain.vo.Position;

import java.util.List;
import java.util.Map;

public class Piece {

    private final Team team;
    private final Type type;

    private Piece(final Team team, final Type type) {
        this.team = team;
        this.type = type;
    }

    public static Piece of(final Team team, final Type type) {
        return new Piece(team, type);
    }

    public boolean isAnotherTeam(final Piece anotherPiece) {
        return this.team != anotherPiece.team;
    }

    public List<Position> getPathPositions(final Position from, final Position to) {
        MoveStrategy strategy = type.getStrategy();
        return strategy.getPath(from, to);
    }

    public boolean canMovePiece(final Position from, final Position to, final Map<Position, Piece> piecesOnPath) {
        MoveStrategy strategy = type.getStrategy();
        return strategy.canMove(this, from, to, piecesOnPath);
    }

    public Type getType() {
        return type;
    }

    public String getTypeName() {
        return type.getName();
    }

    public Team getTeam() {
        return team;
    }

    public String getTeamName() {
        return team.getName();
    }
}
