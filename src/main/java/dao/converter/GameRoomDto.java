package dao.converter;

import domain.piece.character.Team;

public record GameRoomDto(Long id, String name, Team turn) {

}
