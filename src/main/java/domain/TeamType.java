package domain;

import java.util.Arrays;

public enum TeamType {

    CHO,
    HAN;

    public TeamType otherTeam(){
        return Arrays.stream(values())
                .filter(otherTeam -> otherTeam != this)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("다른 팀이 존재하지 않습니다."));
    }
}
