package domain.piece;

import domain.direction.Direction;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.BlockedMovementStrategy;
import java.util.ArrayList;
import java.util.List;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team, PieceType.CHA, new BlockedMovementStrategy());
    }

    @Override
    public Path calculatePath(Position src, Position dest) {
        if(!validateMove(src, dest)) {
            throw new IllegalArgumentException("이동 할 수 있는 경로가 아닙니다.");
        }

        Direction direction = determineDirection(src, dest);

        return bulidPath(src, dest, direction);
    }

    private boolean validateMove(Position src,Position dest) {
        if(src.equals(dest)) return false;

        return src.getX() == dest.getX() || src.getY() == dest.getY();
    }

    private Direction determineDirection(Position src, Position dest) {
        if(src.getX() == dest.getX()) {
            if(src.getY() > dest.getY()) {
                return Direction.SOUTH;
            }
            return Direction.NORTH;
        }

        if(src.getY() == dest.getY()) {
            if(src.getX() > dest.getX()) {
                return Direction.WEST;
            }
            return Direction.EAST;
        }

        throw new IllegalArgumentException("갈 수 있는 경로가 없습니다.");
    }

    private Path bulidPath(Position src, Position dest, Direction direction) {
        List<Position> path = new ArrayList<>();

        Position tmp = direction.move(src);
        path.add(tmp);
        while (!tmp.equals(dest)) {
            tmp = direction.move(tmp);
            path.add(tmp);
        }

        return new Path(src, dest, path);
    }
}
