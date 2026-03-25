package domain;

import java.util.List;

public enum TableSetting {
    LEFT_TABLE(List.of("상", "마", "상", "마")),
    RIGHT_TABLE(List.of("마", "상", "마", "상")),
    INSIDE_TABLE(List.of("마", "상", "상", "마")),
    OUTSIDE_TABLE(List.of("상", "마", "마", "상")),
    ;

    private final List<String> names;

    TableSetting(List<String> names) {
        this.names = names;
    }

    public static TableSetting from(List<String> names) {
        for (TableSetting tableSetting : TableSetting.values()) {
            if (tableSetting.names.equals(names)) {
                return tableSetting;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 상차림입니다.");
    }
}
