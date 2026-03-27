package domain.piece;

import domain.Country;
import domain.Direction;
import domain.Position;
import java.util.List;

public class Cannon extends Piece {
    public Cannon(Country country) {
        super(new PieceInfo(PieceType.CANNON, country));
    }

    @Override
    public List<Direction> findDirections(Position from, Position to) {
        List<Integer> distances = from.calculateDistance(to);
        int x = distances.get(0);
        int y = distances.get(1);

        List<Direction> directions = Direction.findDirections(x, y);
        Direction oneSide = directions.getFirst();
        boolean allSameDirection = directions.stream()
                .allMatch(direction -> direction.equals(oneSide));
        if (!allSameDirection) {
            throw new IllegalArgumentException("[ERROR] 포는 하나의 방향으로만 이동 가능합니다.");
        }
        // 궁성 영역 생각하지 않음
        if (oneSide.isDialog()) {
            throw new IllegalArgumentException("[ERROR] 포는 직선으로만 이동 가능합니다.");
        }
        return directions;
    }
}
