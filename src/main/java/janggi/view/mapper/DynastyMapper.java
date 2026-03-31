package janggi.view.mapper;

import janggi.domain.dynasty.Dynasty;

import java.util.EnumMap;
import java.util.Map;

public class DynastyMapper {

    private static final Map<Dynasty, String> dynastyMap = new EnumMap<>(Dynasty.class);

    static {
        dynastyMap.put(Dynasty.HAN, "한");
        dynastyMap.put(Dynasty.CHO, "초");
    }

    public static String toKorean(Dynasty dynasty) {
        return dynastyMap.get(dynasty);
    }
}
