package janggi.domain.game;

import janggi.domain.dynasty.Dynasty;

public record CurrentTurn(
        Dynasty currentDynasty
) {

    public CurrentTurn changeTurn() {
        return new CurrentTurn(currentDynasty.next());
    }
}
