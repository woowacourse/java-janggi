package view;

import domain.Player;
import domain.Players;
import domain.Team;
import java.util.List;

public class Parser {
    public static Players parseListToPlayers(List<String> playerNames) {
        return new Players(List.of(new Player(playerNames.getFirst(), Team.BLUE),
                new Player(playerNames.getLast(), Team.RED)));
    }
}
