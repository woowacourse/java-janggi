package janggi.domain.piece;

import janggi.domain.Team;

import java.util.Map;

public abstract class Piece {
    protected final Team team;
    private final Position position;
    private final PieceType pieceType;

    public Piece(
            final PieceType pieceType,
            final Position position,
            final Team team
    ) {
        this.pieceType = pieceType;
        this.position = position;
        this.team = team;
    }

    public Position getPosition() {
        return position;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isNone() {
        return pieceType == PieceType.NONE;
    }

    public boolean isNotNone() {
        return !isNone();
    }

    public Piece move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validatePositionToMove(pieces, positionToMove);
        return from(positionToMove);
    }

    public int getScore() {
        return pieceType.getScore();
    }

    public String getName() {
        return pieceType.getName();
    }

    public abstract void validatePositionToMove(Map<Position, Piece> pieces, Position positionToMove);

    public abstract Piece from(Position position);
}
