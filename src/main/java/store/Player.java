package store;

import game.Team;
import location.Position;
import java.util.List;
import piece.Piece;

public class Player {

    private final Pieces pieces;
    private final Team team;

    public Player(Pieces pieces, Team team) {
        this.pieces = pieces;
        this.team = team;
    }

    public void add(Piece piece) {
        pieces.add(piece);
    }

    public Piece getPieceByPoint(Position position) {
        return pieces.getByPosition(position);
    }

    public List<Piece> getPieces() {
        return pieces.getPieces();
    }

    public void delete(Piece piece) {
        pieces.delete(piece);
    }

    public boolean isContainedPiece(Position position) {
        return pieces.isContainedPieceAtPosition(position);
    }

    public boolean isTeam(Team targetTeam) {
        return team.equals(targetTeam);
    }

    private boolean isAlreadyPlayerPieceInPosition(Position position) {
        return pieces.isAlreadyPieceInPosition(position);
    }

    public void validateAlreadyPlayerPieceInDestination(Position end) {
        if (isAlreadyPlayerPieceInPosition(end)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 본인의 기물이 존재합니다.");
        }
    }
}
