package domain.piece;

import static domain.direction.Direction.EAST;
import static domain.direction.Direction.NORTH;
import static domain.direction.Direction.NORTH_EAST;
import static domain.direction.Direction.NORTH_WEST;
import static domain.direction.Direction.SOUTH;
import static domain.direction.Direction.SOUTH_EAST;
import static domain.direction.Direction.SOUTH_WEST;
import static domain.direction.Direction.WEST;

import domain.direction.Direction;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.BlockedMovementStrategy;
import java.util.ArrayList;
import java.util.List;

public class Sang extends Piece {

    private final List<List<Direction>> paths = List.of(
            List.of(NORTH, NORTH_EAST, NORTH_EAST),
            List.of(NORTH, NORTH_WEST, NORTH_WEST),
            List.of(SOUTH, SOUTH_EAST, SOUTH_EAST),
            List.of(SOUTH, SOUTH_WEST, SOUTH_WEST),
            List.of(EAST, NORTH_EAST, NORTH_EAST),
            List.of(EAST, SOUTH_EAST, SOUTH_EAST),
            List.of(WEST, NORTH_WEST, NORTH_WEST),
            List.of(WEST, SOUTH_WEST, SOUTH_WEST)
    );

    public Sang(Team team) {
        super(team, PieceType.SANG, new BlockedMovementStrategy());
    }

    @Override
    public Path calculatePath(Position src, Position dest) {
        for (List<Direction> path : paths) {
            int nextX = src.getX() + path.get(0).getOffsetX() + path.get(1).getOffsetX() + path.get(2).getOffsetX();
            int nextY = src.getY() + path.get(0).getOffsetY() + path.get(1).getOffsetY() + path.get(2).getOffsetX();
            Position nextPosition = new Position(nextX, nextY);
            if (dest.equals(nextPosition)) {
                List<Position> positionList = new ArrayList<>();
                for (Direction direction : path) {
                    positionList.add(
                            new Position(src.getX() + direction.getOffsetX(), src.getY() + direction.getOffsetY()));
                }
                return new Path(src, dest, positionList);
            }
        }
        throw new IllegalArgumentException("목적지로 이동할 수 없습니다.");
    }
}
