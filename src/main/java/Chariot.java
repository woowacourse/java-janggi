import java.util.List;

public class Chariot extends Piece {

    private final List<Route> routes;

    {
        routes = List.of(
            new Route(List.of(new Position(-1, 0))),
            new Route(List.of(new Position(0, 1))),
            new Route(List.of(new Position(1, 0))),
            new Route(List.of(new Position(0, -1)))
        );
    }

    public Chariot(int x, int y) {
        super(x, y);
    }

    @Override
    protected boolean canMove(Board board, int dx, int dy) {
        Position target = position.move(dx, dy);
        for (var route : routes) {
            Position dir = route.positions().getFirst();
            Position nextPos = position.move(dir.x(), dir.y());
            while (board.isInboard(nextPos)) {
                if (nextPos.equals(target)) {
                    return true;
                }
                nextPos = nextPos.move(dir.x(), dir.y());
            }
        }
        return false;
    }
}
