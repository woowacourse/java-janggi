package move;

public class ElephantMovement implements MoveStrategy{

    private static final int[][] MOVEMENT_RANGE_CASES = {{-2, -3}, {-3, -2}, {-3, 2}, {-2, 3},
            {2, 3}, {3, 2}, {3, -2}, {2, -3}};

    @Override
    public Position move(Position from, Position to) {
        for (int i = 0; i < 8; i++) {
            int nx = from.x() + MOVEMENT_RANGE_CASES[i][0];
            int ny = from.y() + MOVEMENT_RANGE_CASES[i][1];

            if (to.equals(new Position(nx, ny))) {
                return to;
            }
        }

        throw new IllegalArgumentException();
    }
}
