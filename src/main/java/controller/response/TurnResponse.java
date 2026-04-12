package controller.response;

import domain.player.Player;
import domain.player.Team;

public record TurnResponse(
        String name,
        Team team
) {

    public static TurnResponse from(final Player player) {
        return new TurnResponse(
                player.getName().name(),
                player.getTeam()
        );
    }
}
