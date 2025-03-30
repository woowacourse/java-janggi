package domain.game.dto;

import domain.player.Player;

public record JanggiGameResponseDto(Long gameId, Player choPlayer, Player hanPlayer) {
}
