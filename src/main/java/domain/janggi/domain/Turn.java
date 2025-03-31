package domain.janggi.domain;

public class Turn {

    private int value = 1;

    public Color getCurrentTurn() {
        if (value % 2 == 0) {
            return Color.RED;
        }
        return Color.BLUE;
    }

    public void increaseRound() {
        ++value;
    }

}
