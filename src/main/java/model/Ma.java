package model;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Ma extends Piece {

    public Ma(Team team) {
        super(team);
        pieceName = PieceName.MA;
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        List<Integer> horizontal = List.of(-1, 1, 2, 2, 1, -1, -2, -2);
        List<Integer> vertical = List.of(2, 2, 1, -1, -2, -2, -1, 1);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforePoint.x() == targetPoint.x()
                        && vertical.get(i) + beforePoint.y() == targetPoint.y());
    }

    @Override
    public Path calculatePath(Point beforePoint, Point targetPoint) {
        // 1. 이동 벡터를 구한다
        // 2. 이동 단위 벡터를 구한다.
        // 3. 단위 벡터 역벡터를 도착점에 더한다.
        // 3.이랑 도착점을 Path에 담아서 리턴

        int vectorX = targetPoint.x() - beforePoint.x();
        int vectorY = targetPoint.y() - beforePoint.y();

        int unitVectorX = vectorX / Math.abs(vectorX);
        int unitVectorY = vectorY / Math.abs(vectorY);

        Point middlePoint = new Point(targetPoint.x() - unitVectorX, targetPoint.y() - unitVectorY);
        Point endPoint = new Point(targetPoint.x(), targetPoint.y());

        Path path = new Path();
        path.addPoint(middlePoint);
        path.addPoint(endPoint);
        return path;
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() == 2) {
            return false;
        }
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            if (!piecesOnPathWithTargetOrNot.values().stream().findFirst().get()) {
                return false;
            }
            return piecesOnPathWithTargetOrNot.keySet().stream().findFirst().get().getTeam() != this.team;
        }
        return true;
    }
}
