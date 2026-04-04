package janggi.exception.business;

import janggi.domain.piece.Team;

import java.util.EnumMap;
import java.util.Map;

public class InvalidTurnException extends BusinessException {
    private static final Map<Team, String> mapper;

    static {
        mapper = new EnumMap<>(Team.class);
        mapper.put(Team.CHO, "초");
        mapper.put(Team.HAN, "한");
    }

    public InvalidTurnException(Team teamName) {
        super("현재 " + mapper.get(teamName) + "나라턴입니다.");
    }
}
