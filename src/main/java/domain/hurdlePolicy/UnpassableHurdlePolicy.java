package domain.hurdlePolicy;

import domain.chesspiece.ChessPiece;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;

public class UnpassableHurdlePolicy implements HurdlePolicy {
    @Override
    public List<ChessPosition> pickDestinations(ChessTeam team, List<Path> coordinates, ChessPiecePositions positions) {
        return coordinates.stream()
                .filter(path -> !existChessPiece(positions, path))
                .map(Path::getDestination)
                .filter(destination -> isAbleToCatch(team, destination, positions))
                .toList();
    }

    private boolean existChessPiece(final ChessPiecePositions positions, final Path path) {
        List<ChessPosition> pathPositions = path.path();
        for (int i = 0; i < pathPositions.size() - 1; i++) {
            ChessPosition currentPosition = pathPositions.get(i);
            if (positions.existPieceByPosition(currentPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAbleToCatch(ChessTeam team, ChessPosition targetPosition, ChessPiecePositions positions) {
        if (!positions.existPieceByPosition(targetPosition)) {
            return true;
        }
        ChessPiece targetPiece = positions.findPieceByPosition(targetPosition);
        return targetPiece.getTeam() != team;
    }
}
