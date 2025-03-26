package domain;

import domain.board.PieceSearcher;
import java.util.Objects;

public class Piece {

    private final Team team;
    private final Coordinate coordinate;
    private final PieceType pieceType;

    public Piece(final Team team, final Coordinate coordinate, final PieceType pieceType) {
        this.team = team;
        this.coordinate = coordinate;
        this.pieceType = pieceType;
    }

    public boolean canMove(final Coordinate arrival, final PieceSearcher pieceSearcher) {
        return pieceType.canMove(coordinate, arrival, pieceSearcher);
    }

    public Piece moveTo(Coordinate arrival) {
        return new Piece(team, arrival, pieceType);
    }

    public final boolean isSameTeam(Piece piece) {
        return team.equals(piece.team);
    }

    public final boolean isTeam(Team team) {
        return this.team.equals(team);
    }

    public final boolean isAt(Coordinate coordinate) {
        return this.coordinate.equals(coordinate);
    }

    public final String getName() {
        return pieceType.getName();
    }

    public final Team getTeam() {
        return team;
    }

    public final Coordinate getCoordinate() {
        return coordinate;
    }

    public final boolean isPo() {
        return pieceType == PieceType.PO;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Piece piece)) {
            return false;
        }

        return team == piece.team && Objects.equals(coordinate, piece.coordinate)
            && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(team);
        result = 31 * result + Objects.hashCode(coordinate);
        result = 31 * result + Objects.hashCode(pieceType);
        return result;
    }
}
