package domain.game;

import domain.piece.Country;

public class Game {

    private final String name;
    private Country currentTurn;

    public Game(String name, Country currentTurn) {
        this.name = name;
        this.currentTurn = currentTurn;
    }

    public void next() {
        currentTurn = currentTurn.convertCountry();
    }

    public String getCurrentName() {
        return currentTurn.name();
    }

    public Country getCountry() {
        return currentTurn;
    }

    public String getName() {
        return name;
    }
    
}
