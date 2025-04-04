package domain.hurdlePolicy;

import domain.chesspiece.ChessPiece;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.ArrayList;
import java.util.List;

public class StopAtHurdlePolicy implements HurdlePolicy {
    @Override
    public List<ChessPosition> pickDestinations(ChessTeam team, List<Path> coordinates, ChessPiecePositions positions) {
        List<ChessPosition> destinations = new ArrayList<>();
        for (Path path : coordinates) {
             destinations.addAll(calculateAvailablePosition(team, positions, path));
        }
        return destinations;
    }

    private List<ChessPosition> calculateAvailablePosition(
            final ChessTeam team,
            final ChessPiecePositions positions,
            final Path path
    ) {
        final List<ChessPosition> chessPositions = new ArrayList<>();
        for (ChessPosition targetPosition : path.path()) {
            if (canMove(team, targetPosition, positions)) {
                chessPositions.add(targetPosition);
            }
            if (positions.existPieceByPosition(targetPosition)) {
                return chessPositions;
            }
        }
        return chessPositions;
    }

    private boolean canMove(ChessTeam team, ChessPosition targetPosition, ChessPiecePositions positions) {
        if (!positions.existPieceByPosition(targetPosition)) {
            return true;
        }
        ChessPiece targetPiece = positions.findPieceByPosition(targetPosition);
        return targetPiece.getTeam() != team;
    }
}
