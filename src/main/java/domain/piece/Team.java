package domain.piece;

import domain.board.Palace;
import java.util.Arrays;

public enum Team {
    HAN(Palace.HAN_PALACE),
    CHO(Palace.CHO_PALACE);

    private final Palace palace;

    Team(Palace palace) {
        this.palace = palace;
    }

    public Palace getPalace() {
        return palace;
    }

    public static Team getTeamByName(String name) {
        return Arrays.stream(Team.values())
                .filter(team -> team.name().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지않는 팀의 이름입니다."));
    }
}
