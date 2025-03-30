package janggi.domain;

import janggi.domain.board.PieceSearcher;

public record Piece(
    Team team,
    Coordinate coordinate,
    PieceType pieceType
) {

    public boolean canMove(final Coordinate arrival, final PieceSearcher pieceSearcher) {
        return pieceType.canMove(coordinate, arrival, pieceSearcher);
    }

    public Piece moveTo(Coordinate arrival) {
        return new Piece(team, arrival, pieceType);
    }

    public boolean isSameTeam(Piece piece) {
        return team.equals(piece.team);
    }

    public boolean isTeam(Team team) {
        return this.team.equals(team);
    }

    public boolean isAt(Coordinate coordinate) {
        return this.coordinate.equals(coordinate);
    }

    public boolean isPo() {
        return pieceType == PieceType.PO;
    }
}
