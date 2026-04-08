package domain.state;

import domain.enums.Country;

public interface State {
    State changeTurn();
    Country getCountry();
    boolean isGameOver();
    default State exitGame() {
        return new ExitGame();
    }
    String getValue();
    static State from(String value) {
        return switch (value) {
            case "ChoTurn" -> new ChoTurn();
            case "HanTurn" -> new HanTurn();
            case "Exit" -> new ExitGame();
            default -> throw new IllegalArgumentException();
        };
    }
}
