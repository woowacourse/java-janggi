package move;

public class HorseMovement implements MoveStrategy {

    private static final int[][] MOVEMENT_RANGE_CASES = {{-1, -2}, {-2, -1}, {-2, 1}, {-1, 2},
            {1, 2}, {2, 1}, {2, -1}, {1, -2}};

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
