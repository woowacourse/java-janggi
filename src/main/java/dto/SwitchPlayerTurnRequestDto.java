package dto;

public record SwitchPlayerTurnRequestDto(
        long teamId,
        boolean isTurn
) {
}
