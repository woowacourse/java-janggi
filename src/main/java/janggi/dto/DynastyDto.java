package janggi.dto;

import janggi.domain.dynasty.Dynasty;
import java.util.EnumMap;
import java.util.Map;

public record DynastyDto(
        String dynastyName
) {

    private static final Map<Dynasty, String> dynastyMap = new EnumMap<>(Dynasty.class);

    static {
        dynastyMap.put(Dynasty.HAN, "한");
        dynastyMap.put(Dynasty.CHO, "초");
    }

    public static DynastyDto from(Dynasty dynasty) {
        return new DynastyDto(dynastyMap.get(dynasty));
    }

}
