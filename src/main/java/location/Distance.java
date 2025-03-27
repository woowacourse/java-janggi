package location;

public record Distance(int x, int y) {

    public static Distance createBy(Position from, Position to) {
        return new Distance(to.x() - from.x(), to.y() - from.y());
    }
}
