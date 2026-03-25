package domain.piece;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Po extends Piece {

    public Po(Team team) {
        super(team);
    }

    @Override
    public Path getPath(Position src, Position dest) {
        List<Position> path = new ArrayList<>();
        if (src.getX() == dest.getX()) {
            if (src.getY() > dest.getY()) {
                for (int i = src.getY() - 1; i >= dest.getY(); i--) {
                    path.add(new Position(src.getX(), i));
                }
                return new Path(src, dest, path);
            }
            for (int i = src.getY() + 1; i < dest.getY(); i++) {
                path.add(new Position(src.getX(), i));
            }
            return new Path(src, dest, path);
        }
        if (src.getX() > dest.getX()) {
            for (int i = src.getX() - 1; i >= dest.getX(); i--) {
                path.add(new Position(i, src.getY()));
            }
            return new Path(src, dest, path);
        }

        for (int i = src.getX() + 1; i <= dest.getX(); i++) {
            path.add(new Position(i, src.getY()));
        }
        return new Path(src, dest, path);
    }
}
