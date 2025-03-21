package board;

import static piece.Team.CHO;
import static piece.Team.HAN;

import piece.Team;

public class Turn {
    private Team currentTeam;

    public static Turn start() {
        return new Turn(CHO);
    }

    private Turn(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public void next() {
        if (currentTeam == CHO) {
            currentTeam = HAN;
        }
        if (currentTeam == HAN){
            currentTeam = CHO;
        }
    }
}
