package janggi.domain.piece;

import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.team.TeamType;

import java.util.List;

public abstract class Piece {

    private final TeamType teamType;
    private final PieceType pieceType;

    public Piece(TeamType teamType, PieceType pieceType) {
        this.teamType = teamType;
        this.pieceType = pieceType;
    }

    public String name() {
        return pieceType.getName();
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public int score() {
        return pieceType.getScore();
    }

    public abstract void validateCanMove(List<Piece> piecesInPath);

    protected abstract List<MovePath> getPaths();

    public abstract List<Position> getPiecePositionsInPath(Position start, Position end);
}
