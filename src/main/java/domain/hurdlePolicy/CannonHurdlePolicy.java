package domain.hurdlePolicy;

import domain.janggiPiece.JanggiChessPiece;
import domain.janggiPiece.Piece;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositions;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;

public class CannonHurdlePolicy implements HurdlePolicy {
    @Override
    public List<JanggiPosition> pickDestinations(JanggiTeam team, List<Path> coordinates, JanggiPositions positions) {
        final List<JanggiPosition> destinations = new ArrayList<>();
        for (Path path : coordinates) {
            List<JanggiPosition> overHurdlePaths = getOverHurdlePaths(path, positions);
            destinations.addAll(getOverHurdleDestinations(team, overHurdlePaths, positions));
        }
        return destinations;
    }

    private List<JanggiPosition> getOverHurdlePaths(Path path, JanggiPositions positions) {
        List<JanggiPosition> pathPositions = path.getPath();
        for (int i = 0; i < pathPositions.size(); i++) {
            JanggiPosition currentPosition = pathPositions.get(i);
            if (isHurdle(currentPosition, positions)) {
                return pathPositions.subList(i + 1, pathPositions.size());
            }
            if (isWall(currentPosition, positions)) {
                return List.of();
            }
        }
        return List.of();
    }

    private boolean isHurdle(JanggiPosition targetPosition, JanggiPositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return false;
        }
        return !isWall(targetPosition, positions);
    }

    private boolean isWall(JanggiPosition targetPosition, JanggiPositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return false;
        }
        JanggiChessPiece other = positions.getJanggiPieceByPosition(targetPosition);
        return other.getChessPieceType() == Piece.CANNON;
    }

    private List<JanggiPosition> getOverHurdleDestinations(
            JanggiTeam team,
            List<JanggiPosition> overHurdlePaths,
            JanggiPositions positions
    ) {
        List<JanggiPosition> result = new ArrayList<>();
        for (JanggiPosition currentPosition : overHurdlePaths) {
            if (canMove(team, currentPosition, positions)) {
                result.add(currentPosition);
            }
            if (positions.existChessPieceByPosition(currentPosition)) {
                return result;
            }
        }
        return result;
    }

    private boolean canMove(JanggiTeam team, JanggiPosition targetPosition, JanggiPositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return true;
        }
        if (isWall(targetPosition, positions)) {
            return false;
        }
        JanggiChessPiece targetPiece = positions.getJanggiPieceByPosition(targetPosition);
        return targetPiece.getTeam() != team;
    }
}
