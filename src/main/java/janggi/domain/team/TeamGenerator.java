package janggi.domain.team;

import janggi.domain.setup.SetupPolicy;

public final class TeamGenerator {

    private TeamGenerator() {

    }

    public static Team generate(final TeamType teamType, final SetupPolicy setupPolicy) {
        if (teamType == TeamType.BLUE) {
            return new BlueTeam(setupPolicy);
        }
        return new RedTeam(setupPolicy);
    }
}
