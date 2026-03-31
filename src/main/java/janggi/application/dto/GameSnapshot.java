package janggi.application.dto;

import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import java.util.List;

public record GameSnapshot(
        Long id,
        Team currentTurn,
        boolean finished,
        Team winner,
        List<PositionInfo> positions
) {
}
