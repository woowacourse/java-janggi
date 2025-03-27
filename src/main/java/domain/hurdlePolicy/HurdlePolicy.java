package domain.hurdlePolicy;

import domain.path.Path;
import domain.position.JanggiPiecePositions;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.util.List;

public interface HurdlePolicy {
    List<JanggiPosition> pickDestinations(JanggiTeam team, List<Path> coordinates, JanggiPiecePositions positions);
}
