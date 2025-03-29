package dao;

import domain.piece.character.PieceType;
import domain.piece.character.Team;

public record PieceEntity(
        Long id, int rowIndex, int columnIndex, PieceType pieceType, Team team, String gameRoomName
) {

}