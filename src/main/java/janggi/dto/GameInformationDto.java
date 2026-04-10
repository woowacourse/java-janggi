package janggi.dto;

import janggi.domain.GameStatus;
import janggi.domain.piece.Team;
import java.util.EnumMap;
import java.util.Map;

public record GameInformationDto(int gameId, String status, String turn) {
    private static final Map<GameStatus, String> statusMapper = new EnumMap<>(GameStatus.class);
    private static final Map<Team, String> teamMapper = new EnumMap<>(Team.class);

    static {
        statusMapper.put(GameStatus.PROGRESS, "진행 중");
        statusMapper.put(GameStatus.END, "종료됨");

        teamMapper.put(Team.CHO, "초");
        teamMapper.put(Team.HAN, "한");
    }

    public static GameInformationDto from(int id, GameStatus status, Team turn) {
        return new GameInformationDto(
                id,
                statusMapper.get(status),
                teamMapper.get(turn)
        );
    }
}
