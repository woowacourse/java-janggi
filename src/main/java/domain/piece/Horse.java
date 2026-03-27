package domain.piece;

import domain.Country;
import domain.Direction;
import domain.Position;
import java.util.List;

public class Horse extends Piece {
    public Horse(Country country) {
        super(new PieceInfo(PieceType.HORSE, country));
    }

    @Override
    public List<Direction> findDirections(Position from, Position to) {
        List<Integer> distances = from.calculateDistance(to);
        int x = distances.get(0);
        int y = distances.get(1);

        List<Direction> directions = Direction.findDirections(x, y);
        if (directions.size() != 2) {
            throw new IllegalArgumentException("[ERROR] 마가 이동할 수 있는 방향은 2개이어야 합니다.");
        }
        if (directions.getFirst().isDialog() || !directions.get(1).isDialog()) {
            throw new IllegalArgumentException("[ERROR] 마의 1번째 방향은 직선이고, 2번째 방향은 대각선이어야 합니다.");
        }
        return directions;
    }
}
