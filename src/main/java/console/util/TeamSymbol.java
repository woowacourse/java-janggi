package console.util;

import janggi.piece.Team;
import java.util.Arrays;

public enum TeamSymbol {
    CHO("초나라"),
    HAN("한나라");

    private final String displayName;

    TeamSymbol(String displayName) {
        this.displayName = displayName;
    }

    public static String from(Team team) {
        return Arrays.stream(values())
                .filter(teamSymbol -> teamSymbol.name().equals(team.name()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 잘못된 팀 정보입니다."))
                .displayName;
    }
}
