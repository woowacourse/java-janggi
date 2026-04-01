package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.side.TeamType;

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

    public abstract void validateCanMove(Position start, Position end, Board board);
}
