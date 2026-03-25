package janggi.domain;

public record Position(int x, int y) {
    public Position move(Movement movement) {
        return new Position(x + movement.getDx(), y + movement.getDy());
    }

    public int calculateDistance(Position position, boolean isVertical) {
        if(isVertical){
            return position.x - this.x;
        }
        return position.y - this.y;
    }

    public boolean isHorizon(Position position) {
        return position.x == this.x;
    }

    public boolean isVertical(Position position) {
        return position.y == this.y;
    }
}
