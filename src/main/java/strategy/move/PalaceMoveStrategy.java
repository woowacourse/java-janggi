package strategy.move;

import domain.board.MovePath;
import domain.board.Position;
import domain.board.Route;
import domain.palace.PalaceRouteGenerator;
import domain.piece.Piece;
import domain.piece.TeamColor;
import java.util.List;
import java.util.Optional;

public class PalaceMoveStrategy implements MoveStrategy {
    private static final PalaceRouteGenerator PALACE_ROUTE_GENERATOR = new PalaceRouteGenerator();

    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        return List.of();
    }

    @Override
    public List<Route> makeRoutes(Position curPos, TeamColor teamColor) {
        return PALACE_ROUTE_GENERATOR.createPalacePieceRoutes(curPos, teamColor);
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece) {
        return blockingPieces.isEmpty();
    }
}
