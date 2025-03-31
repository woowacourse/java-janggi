package dao;

import domain.piece.character.Team;

public record GameRoomDto(String name, Team turn) {

}
