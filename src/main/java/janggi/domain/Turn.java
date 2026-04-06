package janggi.domain;

import janggi.domain.piece.camp.CampType;

public class Turn {

    private CampType currentCampType = CampType.CHO;

    public CampType currentTurn() {
        return currentCampType;
    }

    public CampType peekNextTurn() {
        return currentCampType.next();
    }

    public void finishTurn() {
        currentCampType = currentCampType.next();
    }
}
