package domain.hurdlePolicy;

import domain.chesspiece.ChessPiece;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;
import java.util.ArrayList;
import java.util.List;

public class CannonHurdlePolicy implements HurdlePolicy {
    @Override
    public List<ChessPosition> pickDestinations(ChessTeam team, List<Path> coordinates, ChessPiecePositions positions) {
        final List<ChessPosition> destinations = new ArrayList<>();
        for (Path path : coordinates) {
            List<ChessPosition> overHurdlePaths = calculateOverHurdlePaths(path, positions);
            destinations.addAll(getOverHurdleDestinations(team, overHurdlePaths, positions));
        }
        return destinations;
    }

    private List<ChessPosition> calculateOverHurdlePaths(Path path, ChessPiecePositions positions) {
        List<ChessPosition> pathPositions = path.path();
        for (int i = 0; i < pathPositions.size(); i++) {
            ChessPosition currentPosition = pathPositions.get(i);
            if (isHurdle(currentPosition, positions)) {
                return pathPositions.subList(i + 1, pathPositions.size());
            }
            if (isWall(currentPosition, positions)) {
                return List.of();
            }
        }
        return List.of();
    }

    private boolean isHurdle(ChessPosition targetPosition, ChessPiecePositions positions) {
        if (!positions.existPieceByPosition(targetPosition)) {
            return false;
        }
        return !isWall(targetPosition, positions);
    }

    private boolean isWall(ChessPosition targetPosition, ChessPiecePositions positions) {
        if (!positions.existPieceByPosition(targetPosition)) {
            return false;
        }
        ChessPiece other = positions.findPieceByPosition(targetPosition);
        return other.getChessPieceType() == ChessPieceType.CANNON;
    }

    private List<ChessPosition> getOverHurdleDestinations(
            ChessTeam team,
            List<ChessPosition> overHurdlePaths,
            ChessPiecePositions positions
    ) {
        List<ChessPosition> result = new ArrayList<>();
        for (ChessPosition currentPosition : overHurdlePaths) {
            if (canMove(team, currentPosition, positions)) {
                result.add(currentPosition);
            }
            if (positions.existPieceByPosition(currentPosition)) {
                return result;
            }
        }
        return result;
    }

    private boolean canMove(ChessTeam team, ChessPosition targetPosition, ChessPiecePositions positions) {
        return !positions.existPieceByPosition(targetPosition) || (isHurdle(targetPosition, positions)
                && team != positions.findPieceByPosition(targetPosition).getTeam());
    }
}
