package janggi.palace;

import janggi.board.Position;
import janggi.team.TeamName;
import java.util.List;

public class PalaceFactory {
    public static Palace createPalace(TeamName teamName) {
        if (teamName.matchTeamName("초")) {
            return new Palace(List.of(
                    new Position(3, 0),
                    new Position(4, 0),
                    new Position(5, 0),
                    new Position(3, 1),
                    new Position(4, 1),
                    new Position(5, 1),
                    new Position(3, 2),
                    new Position(4, 2),
                    new Position(5, 2)
            ));
        }
        return new Palace(List.of(
                new Position(3, 7),
                new Position(4, 7),
                new Position(5, 7),
                new Position(3, 8),
                new Position(4, 8),
                new Position(5, 8),
                new Position(3, 9),
                new Position(4, 9),
                new Position(5, 9)
        ));
    }
}
