package janggi.domain;

import janggi.domain.piece.Team;

public class JanggiGame {
    private Team currentTurn = Team.CHO;
    private boolean isFinished = false;


    public boolean isFinished() {
        return isFinished;
    }


    public Team findCurrentTeam() {
        return currentTurn;
    }

    public void changeTurn() {
        if (currentTurn == Team.CHO) {
            currentTurn = Team.HAN;
            return;
        }
        currentTurn = Team.CHO;
    }


}
