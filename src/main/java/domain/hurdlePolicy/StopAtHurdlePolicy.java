package domain.hurdlePolicy;

import domain.janggiPiece.JanggiChessPiece;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositions;
import domain.type.JanggiTeam;

import java.util.ArrayList;
import java.util.List;

public class StopAtHurdlePolicy implements HurdlePolicy {
    @Override
    public List<JanggiPosition> pickDestinations(JanggiTeam team, List<Path> coordinates, JanggiPositions positions) {
        List<JanggiPosition> destinations = new ArrayList<>();
        for (Path path : coordinates) {
            destinations.addAll(getAvailablePosition(team, positions, path));
        }
        return destinations;
    }

    private List<JanggiPosition> getAvailablePosition(
            final JanggiTeam team,
            final JanggiPositions positions,
            final Path path
    ) {
        final List<JanggiPosition> chessPositions = new ArrayList<>();
        for (JanggiPosition targetPosition : path.getPath()) {
            if (canMove(team, targetPosition, positions)) {
                chessPositions.add(targetPosition);
            }
            if (positions.existChessPieceByPosition(targetPosition)) {
                return chessPositions;
            }
        }
        return chessPositions;
    }

    private boolean canMove(JanggiTeam team, JanggiPosition targetPosition, JanggiPositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return true;
        }
        JanggiChessPiece targetPiece = positions.getJanggiPieceByPosition(targetPosition);
        return targetPiece.getTeam() != team;
    }
}
