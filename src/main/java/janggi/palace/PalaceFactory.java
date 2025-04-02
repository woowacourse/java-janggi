package janggi.palace;

import janggi.board.Position;
import janggi.team.TeamName;
import java.util.List;

public enum PalaceFactory {
    TEAM_CHO(new Palace(List.of(
            new Position(3, 0),
            new Position(4, 0),
            new Position(5, 0),
            new Position(3, 1),
            new Position(4, 1),
            new Position(5, 1),
            new Position(3, 2),
            new Position(4, 2),
            new Position(5, 2)

    ))),
    TEAM_HAN(new Palace(List.of(
            new Position(3, 7),
            new Position(4, 7),
            new Position(5, 7),
            new Position(3, 8),
            new Position(4, 8),
            new Position(5, 8),
            new Position(3, 9),
            new Position(4, 9),
            new Position(5, 9)
    )));

    private final Palace palace;

    PalaceFactory(Palace palace) {
        this.palace = palace;
    }

    public static Palace createPalace(TeamName teamName) {
        if (teamName.equals(TeamName.CHO)) {
            return TEAM_CHO.palace;
        }
        return TEAM_HAN.palace;
    }
}
