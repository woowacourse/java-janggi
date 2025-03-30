package dto;

import domain.Team;

public record SwitchPlayerTurnRequestDto(
        Team team,
        boolean isTurn
) {
}
