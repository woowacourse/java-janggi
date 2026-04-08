package domain.palace;

import domain.board.Position;
import domain.board.Route;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.util.List;

public class PalaceRouteGenerator {

    public List<Route> createRoutes(Position position, PieceType pieceType, TeamColor teamColor) {
        if (isKingOrGuard(pieceType)) {
            return createKingAndGuardRoutes(position, teamColor);
        }
        if (pieceType == PieceType.ROOK) {
            return List.of();
        }
        if (pieceType == PieceType.CANNON) {
            return List.of();
        }
        if (pieceType == PieceType.PAWN) {
            return List.of();
        }
        return List.of();
    }

    private List<Route> createKingAndGuardRoutes(Position position, TeamColor teamColor) {
        final Palace palace = Palace.of(teamColor);
        return palace.connectedPositions(position).stream()
                .map(connectedPosition -> new Route(position, connectedPosition, List.of()))
                .toList();
    }

    private boolean isKingOrGuard(PieceType pieceType) {
        return pieceType == PieceType.KING || pieceType == PieceType.GUARD;
    }
}
