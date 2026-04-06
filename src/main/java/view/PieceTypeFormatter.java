package view;

import domain.board.Country;
import domain.piece.PieceType;
import java.util.Arrays;

public enum PieceTypeFormatter {
    CHO_SOLDIER(PieceType.SOLDIER, "卒"),
    HAN_SOLDIER(PieceType.SOLDIER, "兵"),
    GUARD(PieceType.GUARD, "士"),
    ELEPHANT(PieceType.ELEPHANT, "象"),
    HORSE(PieceType.HORSE, "馬"),
    CANNON(PieceType.CANNON, "包"),
    CHARIOT(PieceType.CHARIOT, "車"),
    CHO_GENERAL(PieceType.GENERAL, "楚"),
    HAN_GENERAL(PieceType.GENERAL, "漢"),
    ;

    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String EXIT = "\u001B[0m";

    private final PieceType pieceType;
    private final String pieceName;

    PieceTypeFormatter(PieceType pieceType, String pieceName) {
        this.pieceType = pieceType;
        this.pieceName = pieceName;
    }

    public static String from(PieceType pieceType, Country country) {
        if (pieceType == PieceType.SOLDIER) {
            return getSoldierName(country);
        }
        if (pieceType == PieceType.GENERAL) {
            return getGeneralName(country);
        }

        String pieceName = Arrays.stream(PieceTypeFormatter.values())
                .filter(pieceTypeFormatter -> pieceTypeFormatter.pieceType == pieceType)
                .map(PieceTypeFormatter::getPieceName)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 존재하지 않는 기물 종류입니다."));

        return getOtherName(country, pieceName);
    }

    private static String getSoldierName(Country country) {
        if (country == Country.CHO) {
            return BLUE + CHO_SOLDIER.pieceName + EXIT;
        }
        return RED + HAN_SOLDIER.pieceName + EXIT;
    }

    private static String getGeneralName(Country country) {
        if (country == Country.CHO) {
            return BLUE + CHO_GENERAL.pieceName + EXIT;
        }
        return RED + HAN_GENERAL.pieceName + EXIT;
    }

    private static String getOtherName(Country country, String pieceName) {
        if (country == Country.CHO) {
            return BLUE + pieceName + EXIT;
        }
        return RED + pieceName + EXIT;
    }

    public String getPieceName() {
        return pieceName;
    }
}
