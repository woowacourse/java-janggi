package team.janggi.repository.dto;

import team.janggi.domain.Team;

public record GameRoomInfoDTO(
        Long id,
        Team currentTurn
) {
}
