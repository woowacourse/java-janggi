package model;

import java.util.List;
import java.util.stream.IntStream;

public class Jang extends Piece {

    public Jang(Team team) {
        super(team);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point afterPoint) {
        List<Integer> horizontal = List.of(0, 0, -1, 1);
        List<Integer> vertical = List.of(1, -1, 0, 0);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforePoint.x() == afterPoint.x()
                        && vertical.get(i) + beforePoint.y() == afterPoint.y());
    }

    @Override
    public Path calculatePath(Point beforePoint, Point afterPoint) {
        Path path = new Path();
        path.addPoint(new Point(afterPoint.x(), afterPoint.y()));
        return path;
    }

    @Override
    public boolean isProhibitedPath(List<Piece> piecesOnPath) {
        if (piecesOnPath.size() == 1) {
            Piece piece = piecesOnPath.getFirst();
            if (piece.getTeam() == this.team) {
                return false;
            }
        }
        return true;
    }
}
