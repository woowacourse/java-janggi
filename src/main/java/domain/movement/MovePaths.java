package domain.movement;

import domain.position.Position;

import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Set;

public class MovePaths {

    private final Set<MovePath> movePaths;

    public MovePaths(Set<MovePath> moveActions) {
        this.movePaths = Set.copyOf(moveActions);
    }

    public static MovePaths of(MovePaths existing, MovePath additional) {
        Set<MovePath> combinedPaths = new HashSet<>(existing.movePaths);
        combinedPaths.add(additional);
        return new MovePaths(combinedPaths);
    }

    public double calculateDistance() {
        double distance = getReferenceDistance();
        validateUniformDistance(distance);
        return distance;
    }

    private double getReferenceDistance() {
        return movePaths.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("이동 경로가 존재하지 않습니다."))
                .calculateDistance();
    }

    private void validateUniformDistance(double reference) {
        boolean hasMismatch = movePaths.stream()
                .anyMatch(mp -> Math.abs(mp.calculateDistance() - reference) > 1e-9);
        if (hasMismatch) {
            throw new IllegalArgumentException("이동 액션에 잘못된 값이 들어갔습니다.");
        }
    }

    public MovePath findCorrectMovePath(Position src, Position destination) {
        return movePaths.stream()
                .filter(mp -> mp.canReachDestination(src, destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("목적지에 도착할 수 있는 경로가 존재하지 않습니다."));
    }

    public IntSummaryStatistics xRange() {
        return movePaths.stream()
                .mapToInt(path -> path.getMovements().getFirst().x())
                .summaryStatistics();
    }

    public IntSummaryStatistics yRange() {
        return movePaths.stream()
                .mapToInt(path -> path.getMovements().getFirst().y())
                .summaryStatistics();
    }

    public List<MovePath> getMovePaths() {
        return movePaths.stream().toList();
    }
}
