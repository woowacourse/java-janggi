package model;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Byeong extends Piece {

    public Byeong(Team team) {
        super(team);
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        int moveForward = 1;

        if (team == Team.RED){
            moveForward = -1;
        }

        List<Integer> horizontal = List.of(0, -1, 1);
        List<Integer> vertical = List.of(moveForward, 0, 0);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforePoint.x() == targetPoint.x() && vertical.get(i) + beforePoint.y() == targetPoint.y());
    }

    @Override
    public Path calculatePath(Point beforePoint, Point targetPoint) {
        Path path = new Path();
        path.addPoint(new Point(targetPoint.x(), targetPoint.y()));
        return path;
    }

    @Override
    public boolean canMove(Map<Piece,Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            return piecesOnPathWithTargetOrNot.keySet().stream().findFirst().get().getTeam() != this.team;
        }
        return true;
    }

}
