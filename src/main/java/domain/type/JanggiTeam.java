package domain.type;

import java.util.Arrays;

public enum JanggiTeam {

    RED("RED"),
    BLUE("BLUE"),
    ;

    public final String name;

    JanggiTeam(String name) {
        this.name = name;
    }

    public static JanggiTeam from(String name) {
        return Arrays.stream(values())
                .filter(v -> v.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
    }

    public static JanggiTeam firstTurn() {
        return BLUE;
    }
}
