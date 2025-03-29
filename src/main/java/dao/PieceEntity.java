package dao;

import domain.piece.character.PieceType;
import domain.piece.character.Team;

public record PieceEntity(
        Long id, int rowIndex, int columnIndex, PieceType pieceType, Team team, String gameRoomName
) {

}

// 3개를 주요키로 잡으면 안됨.
// 엔티티의 의미? 용어를 어디서 처음 썼는지? 만든 사람이 누구인지? 위키피디아 영문(한글x).
