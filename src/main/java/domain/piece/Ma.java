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
import domain.position.Position;
import java.util.List;

public class Ma extends Piece {

    private final List<List<Direction>> paths = List.of(
            List.of(NORTH, NORTH_EAST),
            List.of(NORTH, NORTH_WEST),
            List.of(SOUTH, SOUTH_EAST),
            List.of(SOUTH, SOUTH_WEST),
            List.of(EAST, NORTH_EAST),
            List.of(EAST, SOUTH_EAST),
            List.of(WEST, NORTH_WEST),
            List.of(WEST, SOUTH_WEST)
    );

    public Ma(Team team) {
        super(team);
    }

    @Override
    protected List<Position> getRawPositions(Position src) {
        return paths.stream()
                .map(path -> {
                    int x = src.getX() + path.get(0).getOffsetX() + path.get(1).getOffsetX();
                    int y = src.getY() + path.get(0).getOffsetY() + path.get(1).getOffsetY();
                    return new Position(x, y);
                })
                .toList();
    }

    @Override
    public List<Position> getPath(Position src, Position dest) {
        return List.of();
    }
}
