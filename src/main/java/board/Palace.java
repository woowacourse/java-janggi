package board;

import position.Position;

import java.util.Set;

public class Palace {

    // TODO 2025. 3. 30. 12:04: 원래 가운데 Position을 찾는게 아니라
    // TODO 2025. 3. 30. 12:04: - Bound를 정해놓고 그 내의 대각선을 찾는 방식으로 진행하는게 일반적인 방식 적용
    private static final Set<Position> centerPositions;

    static {
        centerPositions = Set.of(
                new Position(5, 2),
                new Position(5, 9)
        );
    }

    public static Set<Position> getCenterPositions() {
        return centerPositions;
    }
}
