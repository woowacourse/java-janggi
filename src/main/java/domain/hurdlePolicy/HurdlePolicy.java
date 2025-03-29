package domain.hurdlePolicy;

import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositions;
import domain.type.JanggiTeam;

import java.util.List;

public interface HurdlePolicy {
    List<JanggiPosition> pickDestinations(JanggiTeam team, List<Path> coordinates, JanggiPositions positions);
}
