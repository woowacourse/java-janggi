package domain.piece;

import domain.Coordinate;
import domain.Team;
import domain.board.PieceFinder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Po extends Piece {

    public Po(Team team) {
        super(team);
    }

    @Override
    protected Set<Coordinate> findMovableCandidates(Coordinate departure) {
        return departure.pickCrossCoordinates();
    }

    @Override
    protected boolean canMoveConsideringObstacles(PieceFinder pieceFinder, Coordinate departure, Coordinate arrival) {
        final var paths = findPaths(departure, arrival);

        final var piecesInPath = pieceFinder.findPiecesIn(paths);
        if (piecesInPath.size() != 1) {
            return false;
        }

        final var podari = piecesInPath.getFirst();
        if (podari.isPo()) {
            return false;
        }

        final var isArrivalPo = pieceFinder.findAt(arrival)
            .map(Piece::isPo)
            .orElse(false);
        return !isArrivalPo;
    }

    @Override
    protected List<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        int dx = arrival.getX() - departure.getX();
        int dy = arrival.getY() - departure.getY();

        List<Coordinate> coordinates = new ArrayList<>();
        if (dx == 0 && dy > 0) { // 아래
            for (int y = departure.getY() + 1; y < arrival.getY(); y++) {
                coordinates.add(new Coordinate(departure.getX(), y));
            }
            return coordinates;
        }
        if (dx == 0 && dy < 0) { // 위
            for (int y = departure.getY() - 1; y > arrival.getY(); y--) {
                coordinates.add(new Coordinate(departure.getX(), y));
            }
            return coordinates;
        }
        if (dx > 0 && dy == 0) { // 오른쪽
            for (int x = departure.getX() + 1; x < arrival.getX(); x++) {
                coordinates.add(new Coordinate(x, departure.getY()));
            }
            return coordinates;
        }
        if (dx < 0 && dy == 0) { // 왼쪽
            for (int x = departure.getX() - 1; x > arrival.getX(); x--) {
                coordinates.add(new Coordinate(x, departure.getY()));
            }
            return coordinates;
        }
        throw new IllegalStateException("유효하지 않은 좌표입니다.");
    }
}
