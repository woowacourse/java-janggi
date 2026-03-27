package janggi.controller.dto.mapper;

import janggi.domain.dynasty.Dynasty;
import java.util.EnumMap;
import java.util.Map;

public class DynastyColorMapper {

    private static final Map<Dynasty, String> dynastyColorMap = new EnumMap<>(Dynasty.class);

    static {
        dynastyColorMap.put(Dynasty.HAN, "\u001B[31m");
        dynastyColorMap.put(Dynasty.CHO, "\u001B[34m");
    }

    private DynastyColorMapper() {
    }

    public static String from(Dynasty dynasty) {
        return dynastyColorMap.get(dynasty);
    }

}
