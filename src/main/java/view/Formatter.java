package view;

import domain.Camp;
import domain.ElephantFormation;
import java.util.EnumMap;
import java.util.Map;

public class Formatter {

    static final Map<Camp, String> CAMP_NAMES = new EnumMap<>(
            Map.of(Camp.CHO, "초나라", Camp.HAN, "한나라"));
    static final Map<ElephantFormation, String> FORMATION_NAMES = new EnumMap<>(
            ElephantFormation.class);

    static {
        FORMATION_NAMES.put(ElephantFormation.RIGHT, "1. [마 상 마 상]");
        FORMATION_NAMES.put(ElephantFormation.INNER, "2. [마 상 상 마]");
        FORMATION_NAMES.put(ElephantFormation.LEFT, "3. [상 마 상 마]");
        FORMATION_NAMES.put(ElephantFormation.OUTER, "4. [상 마 마 상]");
    }

}
