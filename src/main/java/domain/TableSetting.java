package domain;

import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TableSetting {
    LEFT_TABLE(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE), "상마상마"),
    RIGHT_TABLE(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT), "마상마상"),
    INSIDE_TABLE(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE), "마상상마"),
    OUTSIDE_TABLE(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT), "상마마상"),
    ;

    private static final String NOT_FOUND_TABLE_SETTING = "[ERROR] 존재하지 않는 상차림입니다.";

    private final List<PieceType> formation;
    private final String name;

    TableSetting(List<PieceType> formation, String name) {
        this.formation = formation;
        this.name = name;
    }

    public static TableSetting from(String tableSettingName) {
        return Arrays.stream(TableSetting.values())
                .filter(tableSetting -> tableSetting.name.equals(tableSettingName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_TABLE_SETTING));
    }

    public List<PieceType> getFormation(CountryType countryType) {
        List<PieceType> pieceTypes = new ArrayList<>(this.formation);
        if (countryType == CountryType.HAN) {
            Collections.reverse(pieceTypes);
            return pieceTypes;
        }
        return pieceTypes;
    }
}
