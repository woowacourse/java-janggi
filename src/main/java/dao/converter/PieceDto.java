package dao.converter;

import domain.piece.character.PieceType;
import domain.piece.character.Team;

public record PieceDto(
        Long id, int rowIndex, int columnIndex, PieceType pieceType, Team team, String gameRoomName
) {

}