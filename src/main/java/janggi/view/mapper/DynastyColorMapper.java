package janggi.view.mapper;

import janggi.domain.dynasty.Dynasty;
import java.util.EnumMap;
import java.util.Map;

public class DynastyColorMapper {

    private static final Map<Dynasty, String> dynastyColorMap = new EnumMap<>(Dynasty.class);
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    static {
        dynastyColorMap.put(Dynasty.HAN, ANSI_RED);
        dynastyColorMap.put(Dynasty.CHO, ANSI_BLUE);
    }

    private DynastyColorMapper() {
    }

    public static String from(Dynasty dynasty) {
        return dynastyColorMap.get(dynasty);
    }



}
