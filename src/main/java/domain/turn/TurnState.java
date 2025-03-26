package domain.turn;

import domain.TeamType;

public record TurnState(boolean undoLast, TeamType playerTeam) {
}
