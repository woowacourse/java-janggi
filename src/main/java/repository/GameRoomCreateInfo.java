package repository;

import domain.piece.Team;
import domain.state.State;

public record GameRoomCreateInfo(String title, State state, Team turn) {
}
