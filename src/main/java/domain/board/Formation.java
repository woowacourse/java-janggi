package domain.board;

import java.util.Arrays;
import java.util.List;

public enum Formation {
    HORSE_ELEPHANT_HORSE_ELEPHANT(1, List.of(2, 7), List.of(1, 6)),
    HORSE_ELEPHANT_ELEPHANT_HORSE(2, List.of(2, 6), List.of(1, 7)),
    ELEPHANT_HORSE_ELEPHANT_HORSE(3, List.of(1, 6), List.of(2, 7)),
    ELEPHANT_HORSE_HORSE_ELEPHANT(4, List.of(1, 7), List.of(2, 6)),
    ;

    private final int formatNumber;
    private final List<Integer> elephantFormations;
    private final List<Integer> horseFormations;

    Formation(int formatNumber, List<Integer> elephantFormations, List<Integer> horseFormations) {
        this.formatNumber = formatNumber;
        this.elephantFormations = elephantFormations;
        this.horseFormations = horseFormations;
    }

    public static Formation valueOf(int formatNumber) {
        return Arrays.stream(Formation.values())
                .filter(formation -> formation.formatNumber == formatNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상차림 번호입니다."));
    }

    public List<Integer> elephantFormations() {
        return elephantFormations;
    }

    public List<Integer> horseFormations() {
        return horseFormations;
    }
}
