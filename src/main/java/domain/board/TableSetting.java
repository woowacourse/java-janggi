package domain.board;

import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum TableSetting {
    LEFT_TABLE(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE), "상마상마", "왼상차림"),
    RIGHT_TABLE(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT), "마상마상", "오른상차림"),
    INSIDE_TABLE(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE), "마상상마", "안상차림"),
    OUTSIDE_TABLE(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT), "상마마상", "바깥상차림"),
    ;

    private static final String NOT_FOUND_TABLE_SETTING = "[ERROR] 존재하지 않는 상차림입니다.";

    private final List<PieceType> formation;
    private final String formationName;
    private final String displayName;

    TableSetting(List<PieceType> formation, String formationName, String displayName) {
        this.formation = formation;
        this.formationName = formationName;
        this.displayName = displayName;
    }

    public static TableSetting from(String name) {
        for (TableSetting tableSetting : TableSetting.values()) {
            if (tableSetting.formationName.equals(name) || tableSetting.displayName.equals(name)) {
                return tableSetting;
            }
        }
        throw new IllegalArgumentException(NOT_FOUND_TABLE_SETTING);
    }

    public List<PieceType> getFormation(Country country) {
        List<PieceType> pieceTypes = new ArrayList<>(this.formation);
        if (country == Country.HAN) {
            Collections.reverse(pieceTypes);
            return pieceTypes;
        }
        return pieceTypes;
    }

    public String getFormationName() {
        return formationName;
    }
}
