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

public class Sa extends Piece{

    private final List<Direction> paths = List.of(
            NORTH,
            SOUTH,
            EAST,
            WEST,
            NORTH_EAST,
            NORTH_WEST,
            SOUTH_EAST,
            SOUTH_WEST
    );

    public Sa(Team team) {
        super(team);
    }


    @Override
    protected List<Position> getRawPositions(Position src) {
        return paths.stream()
                .map(path -> {
                    int x = src.getX() + path.getOffsetX();
                    int y = src.getY() + path.getOffsetY();
                    return new Position(x, y);
                })
                .toList();
    }

    @Override
    public List<Position> getPath(Position src, Position dest) {
        return List.of();
    }
}
