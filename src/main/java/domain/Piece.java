package domain;

import java.util.List;
import java.util.Optional;
import strategy.move.CannonMoveStrategy;
import strategy.move.ElephantMoveStrategy;
import strategy.move.GuardMoveStrategy;
import strategy.move.HorseMoveStrategy;
import strategy.move.KingMoveStrategy;
import strategy.move.MoveStrategy;
import strategy.move.PawnMoveStrategy;
import strategy.move.RookMoveStrategy;

public class Piece {
    private final TeamColor teamColor;
    private final PieceType pieceType;
    private final MoveStrategy moveStrategy;

    private Piece(TeamColor teamColor, PieceType pieceType, MoveStrategy moveStrategy) {
        this.teamColor = teamColor;
        this.pieceType = pieceType;
        this.moveStrategy = moveStrategy;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public TeamColor getTeamColor() {
        return this.teamColor;
    }

    public List<Route> makeRoutes(Position from) {
        return moveStrategy.makeRoutes(from, teamColor);
    }

    public boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece) {
        return moveStrategy.canMove(route, blockingPieces, destinationPiece, teamColor);
    }

    public static Piece of(TeamColor teamColor, PieceType pieceType) {
        return new Piece(teamColor, pieceType, createMoveStrategy(pieceType));
    }

    private static MoveStrategy createMoveStrategy(PieceType pieceType) {
        return switch (pieceType) {
            case CANNON -> new CannonMoveStrategy();
            case ELEPHANT -> new ElephantMoveStrategy();
            case GUARD -> new GuardMoveStrategy();
            case HORSE -> new HorseMoveStrategy();
            case KING -> new KingMoveStrategy();
            case PAWN -> new PawnMoveStrategy();
            case ROOK -> new RookMoveStrategy();
        };
    }
}
