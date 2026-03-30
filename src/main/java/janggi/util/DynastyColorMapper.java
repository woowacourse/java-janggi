package janggi.util;

import janggi.domain.dynasty.Dynasty;
import java.util.EnumMap;
import java.util.Map;

public class DynastyColorMapper {

    public static final String RED_COLOR = "\u001B[31m";
    public static final String BLUE_COLOR = "\u001B[34m";

    private static final Map<Dynasty, String> dynastyColorMap = new EnumMap<>(Dynasty.class);

    static {
        dynastyColorMap.put(Dynasty.HAN, RED_COLOR);
        dynastyColorMap.put(Dynasty.CHO, BLUE_COLOR);
    }

    private DynastyColorMapper() {
    }

    public static String from(Dynasty dynasty) {
        return dynastyColorMap.get(dynasty);
    }

}
