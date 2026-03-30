package janggi.dto;

import janggi.domain.Position;
import janggi.domain.side.TeamType;

public record BoardSpot(
    Position position,
    String pieceName,
    TeamType teamType
) {

}
