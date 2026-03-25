package domain.piece;

import static domain.direction.Direction.EAST;
import static domain.direction.Direction.NORTH;
import static domain.direction.Direction.SOUTH;
import static domain.direction.Direction.WEST;

import domain.direction.Direction;
import domain.player.Team;
import domain.position.Position;
import java.util.List;

public class Jol extends Piece {

    private List<Direction> paths = List.of();


    public Jol(Team team) {
        super(team);

        if(team.isCho()) {
            paths = List.of(
                    NORTH,
                    EAST,
                    WEST
            );
        }

        if(team.isHan()) {
            paths = List.of(
                    SOUTH,
                    EAST,
                    WEST
            );
        }
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
    protected List<Position> getPaths(Position src, Position dest) {
        return List.of();
    }
}
