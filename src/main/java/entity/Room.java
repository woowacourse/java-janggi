package entity;

import domain.GameState;
import domain.unit.Team;

public record Room(String roomId, GameState status, Team turn) {
}
