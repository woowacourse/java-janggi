package janggi.domain;

import janggi.domain.piece.Camp;

public class Turn {
    private Camp currentCamp = Camp.CHO;

    public Camp currentTurn() {
        return currentCamp;
    }

    public void finishTurn() {
        currentCamp = currentCamp.next();
    }
}
