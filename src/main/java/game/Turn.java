package game;

import piece.Team;

public class Turn {

    private int value;

    public Turn() {
        this.value = 1;
    }

    public Turn(final int value) {
        this.value = value;
    }

    public Team getCurrentTurnTeam() {
        if (value % 2 == 0) {
            return Team.RED;
        }
        return Team.BLUE;
    }

    public void increaseRound() {
        ++value;
    }

    public int getValue() {
        return value;
    }

}
