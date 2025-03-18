package janggi;

public abstract class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public abstract void validateMove(Point fromPoint, Point toPoint);

    protected boolean isBottom() {
        return camp.isBottom();
    }
}
