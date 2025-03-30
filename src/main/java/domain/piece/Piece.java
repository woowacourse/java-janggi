package domain.piece;

import domain.TeamType;
import domain.piece.move.MoveRule;
import domain.piece.path.PathValidator;
import domain.position.Position;
import java.util.List;

public abstract class Piece {

    protected final TeamType teamType;
    protected final MoveRule moveRule;
    protected final PathValidator pathValidator;

    protected Piece(TeamType teamType, MoveRule moveRule, PathValidator pathValidator) {
        this.teamType = teamType;
        this.moveRule = moveRule;
        this.pathValidator = pathValidator;
    }

    public abstract PieceType getType();

    public double getScore() {
        return getType().getScore();
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public boolean isSameTeam(Piece piece) {
        return this.teamType.equals(piece.teamType);
    }

    public boolean isSameTeam(TeamType teamType) {
        return this.teamType.equals(teamType);
    }

    public boolean isSameType(PieceType pieceType) {
        return this.getType().equals(pieceType);
    }

    public void validateMove(Position from, Position to, Board board) {
        List<Position> intermediatePath = moveRule.getIntermediatePath(from, to);
        pathValidator.validatePath(intermediatePath, to, board, this);
    }
}
