package janggi.domain.game;

import janggi.domain.dynasty.Dynasty;

public class CurrentTurn {

    private Dynasty currentDynasty;

    public CurrentTurn(Dynasty currentDynasty) {
        this.currentDynasty = currentDynasty;
    }

    public void changeTurn() {
        this.currentDynasty = currentDynasty.next();
    }

    public Dynasty currentDynasty() {
        return currentDynasty;
    }

}
