package janggi.domain.setting;

import java.util.Arrays;

public enum CampType {
    CHO(9, "초", "궁"),
    HAN(0, "한", "궁"),
    ;

    private final int startYPosition;
    private final String name;
    private final String gungName;

    CampType(final int startYPosition, final String name, final String gungName) {
        this.startYPosition = startYPosition;
        this.name = name;
        this.gungName = gungName;
    }

    public static CampType findCampType(final String turn) {
        return Arrays.stream(CampType.values())
                .filter(campType -> campType.name.equals(turn))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 턴에 해당하는 나라가 존재하지 않습니다."));
    }

    public int getStartYPosition() {
        return startYPosition;
    }

    public String getName() {
        return name;
    }

    public String getGungName() {
        return gungName;
    }
}
