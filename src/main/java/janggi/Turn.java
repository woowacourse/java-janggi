package janggi;

import static janggi.Team.CHO;
import static janggi.Team.HAN;

import janggi.piece.Piece;

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

    public void canMove(Piece piece) {
        if(currentTeam != piece.getTeam()){
            throw new IllegalArgumentException("[ERROR] 같은 팀의 말만 움직일 수 있습니다.");
        }
    }
}
