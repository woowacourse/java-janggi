package domain;

import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;

public class TestUtil {

    public static Position createPosition(int row, int column) {
        return new Position(row, column);
    }

    public static Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }
}
