package domain.piece;

import domain.player.Team;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Po extends Piece {

    public Po(Team team) {
        super(team);
    }

    @Override
    protected List<Position> getRawPositions(Position src) {
        List<Position> rawPositions = new ArrayList<>();

        for (int x = src.getX() + 1; x <= 8; x++) {
            rawPositions.add(new Position(x, src.getY()));
        }
        for (int x = src.getX() - 1; x >= 0; x--) {
            rawPositions.add(new Position(x, src.getY()));
        }
        for (int y = src.getY() + 1; y <= 9; y++) {
            rawPositions.add(new Position(src.getX(), y));
        }
        for (int y = src.getY() - 1; y >= 9; y--) {
            rawPositions.add(new Position(src.getX(), y));
        }
        return rawPositions;
    }

    @Override
    protected List<Position> getPaths(Position src, Position dest) {
        return List.of();
    }
}
