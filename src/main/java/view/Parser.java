package view;

import domain.Team;
import domain.player.Player;
import domain.player.Players;
import java.util.List;

public class Parser {
    public static Players parseListToPlayers(List<String> playerNames) {
        return new Players(List.of(new Player(playerNames.getFirst(), Team.BLUE),
                new Player(playerNames.getLast(), Team.RED)));
    }
}
