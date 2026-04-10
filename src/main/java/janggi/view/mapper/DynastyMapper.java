package janggi.view.mapper;

import janggi.domain.dynasty.Dynasty;

import java.util.EnumMap;
import java.util.Map;

public class DynastyMapper {

    private static final Map<Dynasty, String> dynastyMap = new EnumMap<>(Dynasty.class);
    private static final String ANSI_RESET = "\u001B[0m";

    static {
        dynastyMap.put(Dynasty.HAN, "한");
        dynastyMap.put(Dynasty.CHO, "초");
    }

    public static String toKoreanWithColor(Dynasty dynasty) {
        return DynastyColorMapper.from(dynasty) +
                dynastyMap.get(dynasty) +
                " " + ANSI_RESET;
    }
}
