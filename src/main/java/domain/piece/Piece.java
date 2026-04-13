package domain.piece;

import domain.board.Position;
import domain.board.Route;
import java.util.List;
import java.util.Optional;
import strategy.move.MoveRule;

public class Piece {
    private static final MoveRule MOVE_RULE = new MoveRule();

    private final TeamColor teamColor;
    private final PieceType pieceType;

    private Piece(TeamColor teamColor, PieceType pieceType) {
        this.teamColor = teamColor;
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public TeamColor getTeamColor() {
        return this.teamColor;
    }

    public List<Route> makeRoutes(Position from) {
        return MOVE_RULE.makeRoutes(pieceType, from, teamColor);
    }

    public boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece) {
        return MOVE_RULE.canMove(pieceType, route, blockingPieces, destinationPiece)
                && canOccupy(destinationPiece);
    }

    public boolean isSameTeam(Piece other) {
        return teamColor == other.teamColor;
    }

    public boolean isEnemy(Piece other) {
        return !isSameTeam(other);
    }

    public boolean canOccupy(Optional<Piece> destinationPiece) {
        return destinationPiece.isEmpty() || isEnemy(destinationPiece.get());
    }

    public static Piece of(TeamColor teamColor, PieceType pieceType) {
        return new Piece(teamColor, pieceType);
    }
}
