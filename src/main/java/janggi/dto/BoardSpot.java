package janggi.dto;

import janggi.domain.Position;
import janggi.domain.team.TeamType;

public record BoardSpot(
    Position position,
    String pieceName,
    TeamType teamType
) {

}
