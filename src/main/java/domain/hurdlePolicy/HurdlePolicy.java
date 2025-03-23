package domain.hurdlePolicy;

import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessTeam;

import java.util.List;

public interface HurdlePolicy {
    List<ChessPosition> pickDestinations(ChessTeam team, List<Path> coordinates, ChessPiecePositions positions);
}
