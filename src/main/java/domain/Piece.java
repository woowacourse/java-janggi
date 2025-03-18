package domain;

public abstract class Piece {
    private Position position;

    public Piece(Position position) {
        this.position = position;
    }

    public void moveTo(Position position){
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
