import java.util.List;

public class Elephant extends Piece {

    private final List<Route> routes;

    {
        routes = List.of(
            new Route(List.of(new Position(0, 1), new Position(1, 1), new Position(1, 1))),
            new Route(List.of(new Position(0, 1), new Position(-1, 1), new Position(-1, 1))),

            new Route(List.of(new Position(0, -1), new Position(-1, -1), new Position(-1, -1))),
            new Route(List.of(new Position(0, -1), new Position(1, -1), new Position(1, -1))),

            new Route(List.of(new Position(-1, 0), new Position(-1, 1), new Position(-1, 1))),
            new Route(List.of(new Position(-1, 0), new Position(-1, -1), new Position(-1, -1))),

            new Route(List.of(new Position(1, 0), new Position(1, 1), new Position(1, 1))),
            new Route(List.of(new Position(1, 0), new Position(1, -1), new Position(1, -1)))
        );
    }

    public Elephant(int x, int y) {
        super(x,y);
    }

    @Override
    protected boolean canMove(Board board, int dx, int dy) {
        Position target = position.move(dx, dy);
        for (var route : routes) {
            Position routeSum = route.sum();
            Position expected = position.move(routeSum);
            if (target.equals(expected)) {
                return true;
            }
        }
        return false;}
}
