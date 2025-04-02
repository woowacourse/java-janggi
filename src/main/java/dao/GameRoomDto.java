package dao;

import domain.piece.character.Team;

public record GameRoomDto(Long id, String name, Team turn) {

}
