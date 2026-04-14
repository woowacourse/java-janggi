package domain.board;

import domain.strategy.MoveStrategy;
import domain.vo.Position;

public class Piece {

    private final Team team;
    private final Type type;
    private final MoveStrategy moveStrategy;

    private Piece(final Team team, final Type type, final MoveStrategy moveStrategy) {
        this.team = team;
        this.type = type;
        this.moveStrategy = moveStrategy;
    }

    public static Piece of(final Team team, final Type type, final MoveStrategy moveStrategy) {
        return new Piece(team, type, moveStrategy);
    }

    public boolean isAnotherTeam(final Piece anotherPiece) {
        return this.team != anotherPiece.team;
    }

    public boolean canMovePiece(Position from, Position to, Board board) {
        return moveStrategy.canMove(from, to, board);
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
