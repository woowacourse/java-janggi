package janggi.model;

public class Turn {

    private Color currentColor;

    public Turn(Color currentColor) {
        this.currentColor = currentColor;
    }

    public void nextTurn() {
        currentColor = currentColor.reverse();
    }

    public Color getCurrentTurn() {
        return currentColor;
    }
}
