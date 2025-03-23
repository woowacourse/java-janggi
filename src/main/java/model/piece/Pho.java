package model.piece;

import java.util.Map;
import java.util.Map.Entry;
import model.Path;
import model.Point;
import model.Team;

public class Pho extends Piece {

    public Pho(Team team) {
        super(team);
        pieceName = PieceName.PHO;
    }

    @Override
    public boolean isValidPoint(Point beforePoint, Point targetPoint) {
        boolean isStraightMove = beforePoint.x() == targetPoint.x() || beforePoint.y() == targetPoint.y();
        boolean isSamePoint = beforePoint.equals(targetPoint);

        return isStraightMove && !isSamePoint;
    }

    @Override
    public Path calculatePath(Point beforePoint, Point targetPoint) {
        int vectorX = targetPoint.x() - beforePoint.x();
        int vectorY = targetPoint.y() - beforePoint.y();

        Path path = new Path();

        if (vectorX == 0) {
            int unitVectorY = vectorY / Math.abs(vectorY);
            for (int i = 0; i < Math.abs(vectorY); i++) {
                path.addPoint(new Point(targetPoint.x(), targetPoint.y() - unitVectorY * i));
            }
        }

        if (vectorY == 0) {
            int unitVectorX = vectorX / Math.abs(vectorX);
            for (int i = 0; i < Math.abs(vectorX); i++) {
                path.addPoint(new Point(targetPoint.x() - unitVectorX * i, targetPoint.y()));
            }
        }

        return path;
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() >= 3) {
            return false;
        }
        if (piecesOnPathWithTargetOrNot.size() == 2) {
            return noPhoInObstacles(piecesOnPathWithTargetOrNot)
                    && oneObstacleInTargetPoint(piecesOnPathWithTargetOrNot)
                    && isEnemy(piecesOnPathWithTargetOrNot);
        }
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            return !isTargetPoint(piecesOnPathWithTargetOrNot) &&
                    noPhoInObstacles(piecesOnPathWithTargetOrNot);
        }
        return true;
    }

    private Boolean isTargetPoint(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        return piecesOnPathWithTargetOrNot.values()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("[ERROR] 종점에 위치한 장애물이 존재하지 않습니다.\n"));
    }

    private boolean noPhoInObstacles(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        return piecesOnPathWithTargetOrNot
                .keySet()
                .stream()
                .noneMatch(piece -> piece instanceof Pho);
    }

    private boolean oneObstacleInTargetPoint(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        return piecesOnPathWithTargetOrNot.values()
                .stream()
                .anyMatch(isTargetPoint -> isTargetPoint);
    }

    private boolean isEnemy(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        return piecesOnPathWithTargetOrNot.entrySet()
                .stream()
                .filter(Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("[ERROR] 종점에 위치한 장애물이 존재하지 않습니다.\n"))
                .getKey()
                .getTeam() != this.team;
    }
}
