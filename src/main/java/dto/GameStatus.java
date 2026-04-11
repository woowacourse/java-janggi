package dto;

import model.board.Country;
import model.board.Status;

public record GameStatus(int id, Country turn, String roomName, Status status) {
}
