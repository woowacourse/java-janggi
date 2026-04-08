package janggi.infrastructure.mapper;

import janggi.domain.game.GameStatus;
import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnManager;
import janggi.infrastructure.entity.GameEntity;
import java.util.Arrays;
import java.util.List;

public final class TurnManagerMapper {

    private TurnManagerMapper() {

    }

    public static TurnManager toDomain(final GameEntity gameEntity) {
        final List<Team> teams = Arrays.stream(gameEntity.team_queue().split(","))
            .map(TeamType::valueOf)
            .map(teamType -> {
                if (teamType == TeamType.BLUE) {
                    return new BlueTeam(new InnerElephantSetupPolicy());
                }
                return new RedTeam(new InnerElephantSetupPolicy());
            }).toList();
        return new TurnManager(gameEntity.turns_taken(), teams);
    }

    public static GameEntity toEntity(final int turnsTaken, final List<Team> teams, final
    GameStatus gameStatus) {
        final List<TeamType> teamQueue = teams.stream()
            .map(Team::getTeamType)
            .toList();

        return GameEntity.from("", turnsTaken, teamQueue, gameStatus);
    }
}
