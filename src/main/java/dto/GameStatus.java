package dto;

import model.board.Country;

public record GameStatus(int id, Country turn, String roomName) {
}
