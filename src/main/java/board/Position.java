package board;


public record Position(int x, int y) {

    public double calculateDistance(final Position descPosition) {
        return Math.sqrt(
                Math.pow(Math.abs(this.x - descPosition.x), 2) + Math.pow(Math.abs(this.y - descPosition.y), 2));
    }

    public boolean isSameLine(final Position descPosition) {
        return x == descPosition.x || y == descPosition.y;
    }

    public boolean isXGreaterThan(final Position descPosition) {
        return x >= descPosition.x;
    }

    public boolean isXLessThan(final Position descPosition){
        return x <= descPosition.x;
    }
}
