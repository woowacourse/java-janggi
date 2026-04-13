package dto;

import domain.game.JanggiGame;

public record GameWrapper(long gameId, JanggiGame game) {
}