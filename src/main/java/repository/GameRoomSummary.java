package repository;

import domain.game.GameStatus;
import domain.game.Team;

public record GameRoomSummary(long id, String name, Team currentTurn, GameStatus status) {
}
