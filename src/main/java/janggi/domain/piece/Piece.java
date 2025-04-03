package janggi.domain.piece;

import janggi.domain.Team;
import java.util.Map;
import java.util.Objects;

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

    public static Piece createPiece(PieceType pieceType, Position position, Team team) {
        return switch (pieceType) {
            case PieceType.CANNON -> new Cannon(position, team);
            case PieceType.CHARIOT -> new Chariot(position, team);
            case PieceType.ELEPHANT -> new Elephant(position, team);
            case PieceType.GENERAL -> new General(position, team);
            case PieceType.GUARD -> new Guard(position, team);
            case PieceType.HORSE -> new Horse(position, team);
            case PieceType.SOLDIER -> new Soldier(position, team);
            case PieceType.NONE -> new None(position);
        };
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

    public abstract void validatePositionToMove(Map<Position, Piece> pieces,
        Position positionToMove);

    public abstract Piece from(Position position);

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return team == piece.team && Objects.equals(position, piece.position)
            && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position, pieceType);
    }
}
