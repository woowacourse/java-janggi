package dao;


import domain.piece.*;
import domain.position.Position;

public class PieceFactory {

    public static Piece create(String type, Country country, Position position) {
        return switch (type) {
            case "Cannon" -> new Cannon(position, country);
            case "Chariot" -> new Chariot(position, country);
            case "Elephant" -> new Elephant(position, country);
            case "General" -> new General(position, country);
            case "Guard" -> new Guard(position, country);
            case "Horse" -> new Horse(position, country);
            case "Soldier" -> new Soldier(position, country);
            default -> throw new IllegalArgumentException("존재하지 않는 기물 종류입니다.");
        };
    }
}
