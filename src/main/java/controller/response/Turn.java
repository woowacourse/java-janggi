package controller.response;

import domain.player.Player;
import domain.player.Team;

public record Turn(
        String name,
        Team team
) {

    public static Turn from(final Player player) {
        return new Turn(
                player.getName().name(),
                player.getTeam()
        );
    }
}
