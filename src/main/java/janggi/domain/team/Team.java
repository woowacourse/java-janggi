package janggi.domain.team;

import janggi.domain.Pieces;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.dto.BoardSpots;
import java.util.Optional;

public class Team {

    private final TeamType teamType;
    private final Pieces pieces;

    private Team(TeamType teamType, Pieces pieces) {
        this.teamType = teamType;
        this.pieces = pieces;
    }

    public static Team createInitialTeam(TeamType teamType) {
        return new Team(teamType, Pieces.create(teamType));
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public BoardSpots makeSnapShot() {
        return pieces.makeSnapShot();
    }

    public Optional<Piece> findPiece(Position position) {
        return pieces.findPiece(position);
    }

    public boolean hasPieceType(PieceType pieceType) {
        return pieces.hasPieceType(pieceType);
    }

    public Team remove(Position position) {
        return new Team(teamType, pieces.remove(position));
    }

    public Team move(Position piecePosition, Position targetPosition) {
        return new Team(teamType, pieces.move(piecePosition, targetPosition));
    }

    public int calculatePiecesScore() {
        return pieces.sumScore();
    }
}
