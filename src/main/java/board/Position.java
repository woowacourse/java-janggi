package board;


public record Position(int x, int y) {

    public double calculateDistance(final Position descPosition) {
        return Math.sqrt(
                Math.pow(Math.abs(this.x - descPosition.x), 2) + Math.pow(Math.abs(this.y - descPosition.y), 2));
    }
}
