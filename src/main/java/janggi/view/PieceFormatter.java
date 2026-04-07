package janggi.view;

import janggi.dto.PieceDto;
import java.util.Map;

public class PieceFormatter {
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    private static final Map<String, String> SYMBOLS = Map.of(
            "GENERAL", "궁",
            "CHARIOT", "차",
            "CANNON", "포",
            "HORSE", "마",
            "ELEPHANT", "상",
            "GUARD", "사",
            "SOLDIER", "졸"
    );

    public static String format(PieceDto pieceDto) {
        String name = getName(pieceDto);
        String color = getColor(pieceDto.side());

        return color + name + RESET;
    }

    private static String getName(PieceDto pieceDto) {
        String type = pieceDto.type();
        String side = pieceDto.side();

        if ("SOLDIER".equals(type) && "HAN".equals(side)) {
            return "병";
        }

        if (!SYMBOLS.containsKey(type)) {
            throw new IllegalArgumentException("지원하지 않는 기물 타입입니다: " + type);
        }

        return SYMBOLS.get(type);
    }

    private static String getColor(String side) {
        if ("HAN".equals(side)) {
            return RED;
        }
        return BLUE;
    }
}
