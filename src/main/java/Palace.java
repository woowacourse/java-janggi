import java.util.List;

public class Palace extends Piece {

    private final List<Route> routes;

    {
        routes = List.of(
            new Route(List.of(new Position(-1, 0))),
            new Route(List.of(new Position(0, 1))),
            new Route(List.of(new Position(1, 0))),
            new Route(List.of(new Position(0, -1)))
        );
    }

    public Palace(int x, int y) {
        super(x, y);
    }

    @Override
    protected boolean canMove(int x, int y) {
        for (var route : routes) {
            // int sumX = route.positions().stream()
            //     .mapToInt(Position::x)
            //     .sum();
            // int sumY = route.positions().stream()
            //     .mapToInt(Position::y)
            //     .sum();
            Position routeSum = route.sum();
            Position difference = routeSum.differenceWith(position);
            if (position.differenceWith(new Position(x, y)).equals(difference)) {
                return true;
            }
        }
        return false;
/*        for (var route : routes) {
            int i = 0;
            boolean flag = true;
            for (; i < route.positions().size() - 1; i++) {
                Position position = route.positions().get(i);
                // TODO 다른 기물이 존재하는지 확인 필요
                // if (this.position == position) {
                //     못감
                    // flag = false;
                    // break;
                // }
            }
            if (i == route.positions().size() - 1
                && flag) {
                // 가능
                return true;
            }
        }*/
        // return false;
    }
}
