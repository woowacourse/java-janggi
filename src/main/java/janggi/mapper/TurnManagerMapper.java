package janggi.mapper;

import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnManager;
import janggi.entity.GameEntity;
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
}
