package domain.game;

import domain.piece.Country;

public class Game {

    private final Long id;
    private final String name;
    private Country currentTurn;

    public Game(Long id, String name, Country currentTurn) {
        this.id = id;
        this.name = name;
        this.currentTurn = currentTurn;
    }

    public Game(String name, Country currentTurn) {
        this(null, name, currentTurn);
    }

    public void next() {
        currentTurn = currentTurn.convertCountry();
    }

    public Long getId() {
        return id;
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
