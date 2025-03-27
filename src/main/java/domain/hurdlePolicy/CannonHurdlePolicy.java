package domain.hurdlePolicy;

import domain.janggiPiece.JanggiPiece;
import domain.path.Path;
import domain.position.JanggiPiecePositions;
import domain.position.JanggiPosition;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;

public class CannonHurdlePolicy implements HurdlePolicy {
    @Override
    public List<JanggiPosition> pickDestinations(JanggiTeam team, List<Path> coordinates, JanggiPiecePositions positions) {
        final List<JanggiPosition> destinations = new ArrayList<>();
        for (Path path : coordinates) {
            List<JanggiPosition> overHurdlePaths = getOverHurdlePaths(team, path, positions);
            destinations.addAll(getOverHurdleDestinations(team, overHurdlePaths, positions));
        }
        return destinations;
    }

    private List<JanggiPosition> getOverHurdlePaths(JanggiTeam team, Path path, JanggiPiecePositions positions) {
        List<JanggiPosition> pathPositions = path.getPath();
        for (int i = 0; i < pathPositions.size(); i++) {
            JanggiPosition currentPosition = pathPositions.get(i);
            if (isHurdle(team, currentPosition, positions)) {
                return pathPositions.subList(i+1, pathPositions.size());
            }
            if (isWall(currentPosition, positions)) {
                return List.of();
            }
        }
        return List.of();
    }

    private boolean isHurdle(JanggiTeam team, JanggiPosition targetPosition, JanggiPiecePositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return false;
        }
        JanggiPiece other = positions.getJanggiPieceByPosition(targetPosition);
        return !isWall(targetPosition, positions) && team != other.getTeam();
    }

    private boolean isWall(JanggiPosition targetPosition, JanggiPiecePositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return false;
        }
        JanggiPiece other = positions.getJanggiPieceByPosition(targetPosition);
        return other.getChessPieceType() == JanggiPieceType.CANNON;
    }

    private List<JanggiPosition> getOverHurdleDestinations(
            JanggiTeam team,
            List<JanggiPosition> overHurdlePaths,
            JanggiPiecePositions positions
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

    private boolean canMove(JanggiTeam team, JanggiPosition targetPosition, JanggiPiecePositions positions) {
        return !positions.existChessPieceByPosition(targetPosition) || isHurdle(team, targetPosition, positions);
    }
}
