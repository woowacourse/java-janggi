package janggi.util;

import janggi.domain.board.HorseElephantPosition;
import java.util.HashMap;
import java.util.Map;

public class HorseElephantPositionMapper {

    public static final int HEHE_ORDINAL = 1;
    public static final int HEEH_ORDINAL = 2;
    public static final int EHEH_ORDINAL = 3;
    public static final int EHHE_ORDINAL = 4;

    private static final Map<Integer, HorseElephantPosition> horseElephantPositionMap = new HashMap<>();

    static {
        horseElephantPositionMap.put(HEHE_ORDINAL, HorseElephantPosition.HEHE);
        horseElephantPositionMap.put(HEEH_ORDINAL, HorseElephantPosition.HEEH);
        horseElephantPositionMap.put(EHEH_ORDINAL, HorseElephantPosition.EHEH);
        horseElephantPositionMap.put(EHHE_ORDINAL, HorseElephantPosition.EHHE);
    }

    private HorseElephantPositionMapper() {
    }

    public static HorseElephantPosition from(int ordinal) {
        return horseElephantPositionMap.get(ordinal);
    }

}
