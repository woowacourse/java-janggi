package domain;

public class Player {
    private final Name name;
    private TurnState turnState;

    public Player(Name name, TurnState turnState) {
        this.name = name;
        this.turnState = turnState;
    }

    public boolean isTurn() {
        return turnState.isCurrent();
    }

    public void changeTurn() {
        turnState = turnState.next();
    }
}
