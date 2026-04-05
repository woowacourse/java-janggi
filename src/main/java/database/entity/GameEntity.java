package database.entity;

import domain.game.Team;

public record GameEntity(int id, String status, Team currentTurn) {
}
