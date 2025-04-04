package entity;

import domain.Team;
import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.Elephant;
import domain.pieces.General;
import domain.pieces.Guard;
import domain.pieces.Horse;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import execptions.JanggiArgumentException;

public class PieceMapper {
    public static Piece toPiece(PieceEntity pieceEntity, TeamEntity teamEntity) {
        String type = pieceEntity.getType();
        Team team = Team.findByName(teamEntity.getName());

        return switch (type) {
            case "Soldier" -> new Soldier(team);
            case "Elephant" -> new Elephant(team);
            case "Horse" -> new Horse(team);
            case "Cannon" -> new Cannon(team);
            case "Chariot" -> new Chariot(team);
            case "General" -> new General(team);
            case "Guard" -> new Guard(team);
            default -> throw new JanggiArgumentException("타입에 해당하는 기물이 존재하지 않습니다.");
        };
    }
}
