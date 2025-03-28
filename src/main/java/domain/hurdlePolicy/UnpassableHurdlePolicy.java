package domain.hurdlePolicy;

import domain.janggiPiece.JanggiChessPiece;
import domain.path.Path;
import domain.position.JanggiPiecePositions;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.util.List;

public class UnpassableHurdlePolicy implements HurdlePolicy {
    @Override
    public List<JanggiPosition> pickDestinations(JanggiTeam team, List<Path> coordinates, JanggiPiecePositions positions) {
        return coordinates.stream()
                .filter(path -> !existChessPiece(positions, path))
                .map(Path::getDestination)
                .filter(destination -> isAbleToCatch(team, destination, positions))
                .toList();
    }

    private boolean existChessPiece(final JanggiPiecePositions positions, final Path path) {
        List<JanggiPosition> pathPositions = path.getPath();
        for (int i = 0; i < pathPositions.size() - 1; i++) {
            JanggiPosition currentPosition = pathPositions.get(i);
            if (positions.existChessPieceByPosition(currentPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAbleToCatch(JanggiTeam team, JanggiPosition targetPosition, JanggiPiecePositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return true;
        }
        JanggiChessPiece targetPiece = positions.getJanggiPieceByPosition(targetPosition);
        return targetPiece.getTeam() != team;
    }
}
