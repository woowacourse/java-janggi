package repository;

import domain.piece.Team;
import domain.state.State;

public record GameRoomInfo(long id, String title, State state, Team turn) {
}
