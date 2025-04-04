package domain;

import execptions.JanggiArgumentException;
import java.util.Arrays;

public enum Team {
    HAN(false),
    CHO(true),
    NONE(false);

    private final boolean isFirst;

    Team(final boolean isFirst) {
        this.isFirst = isFirst;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public static Team findByName(String name) {
        return Arrays.stream(Team.values())
                .filter(team -> name.equals(team.name()))
                .findAny()
                .orElseThrow(() -> new JanggiArgumentException("조건에 해당하는 팀이 존재하지 않습니다."));
    }
}
