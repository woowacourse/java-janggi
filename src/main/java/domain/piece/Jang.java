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

public class Jang extends Piece {

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

    public Jang(Team team) {
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
        for(Direction path : paths) {
            int x = src.getX() + path.getOffsetX();
            int y = src.getY() + path.getOffsetY();
            Position nextPosition = new Position(x,y);
            if(dest.equals(nextPosition)) {
                return List.of(nextPosition);
            }
        }
        throw new IllegalArgumentException("목적지로 이동할 수 없습니다.");
    }

}
