package movement;

import position.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovePaths {

    private final Set<MovePath> movePaths;

    public MovePaths(List<MovePath> moveActions) {
        this.movePaths = new HashSet<>(moveActions);
    }

    // TODO 2025. 3. 28. 17:26: 좀 더 가독성 좋은 매개인자 합치기 코드 필요
    public MovePaths(MovePaths movePaths, MovePath movePath) {
        Set<MovePath> newMovePaths = new HashSet<>(movePaths.getMovePaths());
        newMovePaths.add(movePath);
        this.movePaths = newMovePaths;
    }

    // todo: 뭔가 더 깔끔한 코드 없을까?? validate메서드와의 연결이 좀 더 깔끔헀으면 좋겠어
    public double calculateDistance() {
        List<MovePath> list = movePaths.stream().limit(1).toList();
        double distance = list.getFirst().calculateDistance();
        validateMoveDistance(distance);
        return distance;
    }

    private void validateMoveDistance(double origin) {
        for (MovePath moveAction : movePaths) {
            double compare = moveAction.calculateDistance();
            if (Math.abs(origin - compare) > 1e-9) {
                throw new IllegalArgumentException("이동 액션에 잘못된 값이 들어갔습니다.");
            }
        }
    }

    public MovePath findCorrectMovePath(Position src, Position destination) {
        for (MovePath movePath : movePaths) {
            if (movePath.canReachDestination(src, destination)) {
                return movePath;
            }
        }
        throw new IllegalArgumentException("목적지에 도착할 수 있는 경로가 존재하지 않습니다.");
    }

    public List<MovePath> getMovePaths() {
        return movePaths.stream().toList();
    }
}
