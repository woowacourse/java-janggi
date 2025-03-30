package janggi.dto;

import janggi.position.Position;
import janggi.team.Team;

public record BoardPieceDto(Team team, Position position, boolean isLive) {
}
