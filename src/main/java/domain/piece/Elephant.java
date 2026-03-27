package domain.piece;

import domain.Country;
import domain.Direction;
import domain.Position;
import java.util.List;

public class Elephant extends Piece {
    public Elephant(Country country) {
        super(new PieceInfo(PieceType.ELEPHANT, country));
    }

    @Override
    public List<Direction> findDirections(Position from, Position to) {
        List<Integer> distances = from.calculateDistance(to);
        int x = distances.get(0);
        int y = distances.get(1);

        List<Direction> directions = Direction.findDirections(x, y);
        if (directions.size() != 3) {
            throw new IllegalArgumentException("[ERROR] 상이 이동할 수 있는 방향은 3개이어야 합니다.");
        }
        if (directions.get(1) != directions.get(2)) {
            throw new IllegalArgumentException("[ERROR] 상의 2번째 방향과 3번째 방향은 동일해야 합니다.");
        }
        if (directions.getFirst().isDialog() || !directions.get(1).isDialog()) {
            throw new IllegalArgumentException("[ERROR] 상의 1번째 방향은 직선이고, 2, 3번째 방향은 대각선이어야 합니다.");
        }
        return directions;
    }
}
