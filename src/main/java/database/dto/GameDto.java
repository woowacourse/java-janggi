package database.dto;

import domain.game.Team;

public record GameDto(int id, Team currentTurn) {
}
