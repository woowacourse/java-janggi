package domain;

import domain.strategy.MoveStrategy;
import domain.vo.Position;

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

    public boolean canMovePiece(Position from, Position to, Board board) {
        MoveStrategy strategy = type.getStrategy();
        return strategy.canMove(from, to, board);
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
