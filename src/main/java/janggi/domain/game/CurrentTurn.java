package janggi.domain.game;

import janggi.domain.dynasty.Dynasty;

public class CurrentTurn {

    private Dynasty currentDynasty;

    private CurrentTurn(Dynasty currentDynasty) {
        this.currentDynasty = currentDynasty;
    }

    public static CurrentTurn of(Dynasty dynasty) {
        return new CurrentTurn(dynasty);
    }

    public static CurrentTurn from(String dynasty) {
        return new CurrentTurn(Dynasty.valueOf(dynasty));
    }

    public void changeTurn() {
        this.currentDynasty = currentDynasty.next();
    }

    public Dynasty currentDynasty() {
        return currentDynasty;
    }

}
