package janggi.controller.dto.mapper;

import janggi.domain.board.HorseElephantPosition;
import java.util.HashMap;
import java.util.Map;

public class HorseElephantPositionMapper {

    private static final Map<Integer, HorseElephantPosition> horseElephantPositionMap = new HashMap<>();

    static {
        horseElephantPositionMap.put(1, HorseElephantPosition.HEHE);
        horseElephantPositionMap.put(2, HorseElephantPosition.HEEH);
        horseElephantPositionMap.put(3, HorseElephantPosition.EHEH);
        horseElephantPositionMap.put(4, HorseElephantPosition.EHHE);
    }

    private HorseElephantPositionMapper() {
    }

    public static HorseElephantPosition from(int ordinal) {
        return horseElephantPositionMap.get(ordinal);
    }

}
