package view.mapper;

import model.Team;
import model.piece.PieceType;

import java.util.EnumMap;
import java.util.Map;

public class ViewMapper {

    private static final Map<PieceType, Map<Team, String>> SYMBOL_MAP = new EnumMap<>(PieceType.class);

    static {
        SYMBOL_MAP.put(PieceType.CHARIOT, Map.of(Team.HAN, "車", Team.CHO, "車"));
        SYMBOL_MAP.put(PieceType.CANNON, Map.of(Team.HAN, "包", Team.CHO, "包"));
        SYMBOL_MAP.put(PieceType.GENERAL, Map.of(Team.HAN, "漢", Team.CHO, "楚"));
        SYMBOL_MAP.put(PieceType.HORSE, Map.of(Team.HAN, "馬", Team.CHO, "馬"));
        SYMBOL_MAP.put(PieceType.ELEPHANT, Map.of(Team.HAN, "象", Team.CHO, "象"));
        SYMBOL_MAP.put(PieceType.GUARD, Map.of(Team.HAN, "士", Team.CHO, "士"));
        SYMBOL_MAP.put(PieceType.SOLDIER, Map.of(Team.HAN, "兵", Team.CHO, "卒"));
    }

    private ViewMapper() {
    }

    public static String getSymbol(PieceType type, Team team) {
        return SYMBOL_MAP.get(type).get(team);
    }
}
