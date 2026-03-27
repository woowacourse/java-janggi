package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.Route;
import domain.TeamColor;
import java.util.List;
import java.util.Optional;

public class CannonMoveStrategy implements MoveStrategy {

    private static final List<MovePath> PATHS = List.of(
            new MovePath(List.of(Direction.NORTH)),
            new MovePath(List.of(Direction.SOUTH)),
            new MovePath(List.of(Direction.EAST)),
            new MovePath(List.of(Direction.WEST))
    );

    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        return PATHS;
    }

    @Override
    public boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece, TeamColor myTeam) {
        if (blockingPieces.isEmpty()) {
            return false;
        }

        if (blockingPieces.size() != 1) {
            return false;
        }

        Piece bridgePiece = blockingPieces.getFirst();
        if (bridgePiece.getPieceType() == PieceType.CANNON) {
            return false;
        }

        if (destinationPiece.isEmpty()) {
            return true;
        }

        Piece targetPiece = destinationPiece.get();
        if (targetPiece.getPieceType() == PieceType.CANNON) {
            return false;
        }

        return targetPiece.getTeamColor() != myTeam;
    }
}
